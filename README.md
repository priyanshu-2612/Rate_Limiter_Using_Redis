***Redis Rate Limiter***
--------
* This project exposes a /api/data endpoint which can be accessed using **HTTP GET** request.
* The endpoint returns a simple string "Hello World!"
* When a request to this endpoint is made, a key (IP adress + Route) is created in Redis and given a value of 0. It's expiry is set to 60 seconds. With each request the value is incremented
and if it exceeds the rate limit no more requests for that IP are allowed.
