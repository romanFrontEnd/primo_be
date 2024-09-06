package com.bloxgaming.primo;

import java.nio.file.Paths;

import java.util.*;

import static com.bloxgaming.primo.Utils.*;
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.staticFiles;
import static spark.Spark.port;

import com.bloxgaming.primo.entity.CacheRecord;
import com.bloxgaming.primo.entity.User;
import com.bloxgaming.primo.request.VerifyRequest;
import com.bloxgaming.primo.response.GameHistoryResponse;
import com.bloxgaming.primo.response.GameResult;
import com.bloxgaming.primo.response.PlayGame;
import com.bloxgaming.primo.response.ServerSeedResponse;
import com.bloxgaming.primo.services.CacheService;
import com.google.gson.Gson;


public class Server {
  private static Gson gson = new Gson();
  private static int MIN_BOUND=  1;
  private static int MAX_BOUND=  20;

  private  static int SERVER_PORT = 4242;

  private static String clientSeed="AABB112233";

  private static User currentUser;

  private static List<Integer> gameResults;

  private static CacheService cacheService;

  static void initCache() {
    cacheService = new CacheService();
  }


  public static void main(String[] args) {
    port(SERVER_PORT);

    initCache();

    // save the server seed into the users "table"
    currentUser = new User(generateUserId(), generateServerSeed());

    // game results
    gameResults = new ArrayList<>();

    staticFiles.externalLocation(Paths.get("public").toAbsolutePath().toString());

    get("/api/health", (request, response) -> "Server is UP");

    get("/api/hashed-server-seed", (request, response) -> {

      // return a hashed version of the server seed so the client can verify that it wasn't altered
      ServerSeedResponse serverSeedResponse = new ServerSeedResponse(
              sha512(currentUser.getServerSeed()),
              currentUser.getId());
      return gson.toJson(serverSeedResponse);
    });

    get("/api/spin/result", (request, response) -> {
      response.type("application/json");
      //seed generated on the client
      String clientSeed = request.queryParams("clientSeed");

      String serverSeed = currentUser.getServerSeed();

      //get nonce by incrementing the number of results
      String nonce = String.valueOf(gameResults.size());
      // combination
      String combination = combine(serverSeed, clientSeed, nonce);
      int gameResult = getResult(sha512(combination));

      PlayGame playGame = new PlayGame(gameResult, serverSeed, nonce);
      // NEW SERVER SEED
      // generate a new server seed
      currentUser.setServerSeed(generateServerSeed());
      // RESULT RETURNING
      // insert into our "database" the new result
      gameResults.add(gameResult);


      boolean isPrime = isPrime(gameResult);
      String winOrLose = (isPrime) ? "WIN" : "LOSE";
      //store record to cache
      cacheService.storeSpins(currentUser.getId(), playGame, isPrime, winOrLose);

      return gson.toJson(playGame);
    });

    post("/api/spin/verify", (request, response) -> {

      // get the data provided by the client: clientSeed, serverSeed, nonce
      VerifyRequest verifyBody = gson.fromJson(request.body(), VerifyRequest.class);

      // create a combination from the provided data
      String combination = combine(verifyBody.getServerSeed(), verifyBody.getClientSeed(), verifyBody.getNonce());
      // generate a result from the data
      int result = getResult(sha512(combination));

      boolean isPrime = isPrime(result);
      String winOrLose = (isPrime) ? "WIN" : "LOSE";

      GameResult gameResult = new GameResult(isPrime, result, winOrLose);
      // return to client
      return gson.toJson(gameResult);
    });

    get("/api/spin/history", (request, response) -> {
      String userId = request.queryParams("id");
      List<CacheRecord> history = cacheService.get(userId);
      GameHistoryResponse gameHistoryResponse = new GameHistoryResponse(userId, history);
      return gson.toJson(gameHistoryResponse);
    });
  }

}