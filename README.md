# Primo game BE

This is Primo Game BE

## Running the sample

1. Build the server

~~~
mvn package
~~~

2. Run the server

~~~
java -cp target/sample-jar-with-dependencies.jar com.bloxgaming.primo.Server
~~~

3. Check server is up go to [http://localhost:4242/health](http://localhost:4242/health)

4. Install Postman and Import postman collection from the project source dir

the file name- BloxGaming.postman_collection.json

5. API description:

First you need to obtain server seed ( server side secret according to https://bgaming.com/provably-fair)
GET localhost:4242/api/hashed-server-seed
~~~
Here you will obtain hashed server seed and userId
UUID - user id
servseed - hashed for security reasons
~~~

Secondly call: GET localhost:4242/api/spin/result?clientSeed=AABB112233
~~~

clientSeed=AABB112233 shoud be generated on you side use this function for example
const randomString = length => {
  const availableChars =
    'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
  let randomString = '';
  for (let i = 0; i < length; i++) {
    randomString +=
      availableChars[Math.floor(Math.random() * availableChars.length)];
  }
  return randomString;
};

In a response you will obtain:
{
    "result": 0,
    "serverSeed": "5645f74e7f0ac61e46926efbe29119a9fae62847659ab308c76f8e7ff80921ec7cfaa93275357383d5486b869e4c1153d7788f5fa44f90aa47351ccacfb05c87c87ed1dc684773edb7f6cdb2a793aa611cd165a3961976d23e4d4112e96b6f5674a1f23a16c7578068a9258ba3cfb237685de28ee7d2d16037f7e66e94869ed0",
    "nonce": "2"
}

"result": 0 - generated on server
serverSeed - remember it you will need it on the next step during verification
nonce - it's you spin attempt also remember it
~~~

The last call FE->BE is verification of the game result
POST localhost:4242/api/spin/verify
~~~
Body:
{"clientSeed": "AABB112233", "nonce":"2", "serverSeed": "5645f74e7f0ac61e46926efbe29119a9fae62847659ab308c76f8e7ff80921ec7cfaa93275357383d5486b869e4c1153d7788f5fa44f90aa47351ccacfb05c87c87ed1dc684773edb7f6cdb2a793aa611cd165a3961976d23e4d4112e96b6f5674a1f23a16c7578068a9258ba3cfb237685de28ee7d2d16037f7e66e94869ed0"}
clientSeed - it is generated on FE during Play game call GET localhost:4242/api/spin/result?clientSeed=AABB112233
nonce - it's you current spin attempt ( grab it from Play game response)
serverSeed - it's you current spin server seed ( grab it from Play game response)
~~~


In order to get history of spins by userId: GET localhost:4242/api/spin/history?id=ef8903f2-d177-4c81-aff9-0bdfec406582

~~~
id - is uuid user id 

In order to pretify Json use service: https://jsonformatter.curiousconcept.com/#
Just copy paste you response
~~~
