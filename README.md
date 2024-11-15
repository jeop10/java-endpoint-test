# DEMO APP

This springboot app is a simple REST API that has the following endpoints

| Endpoint    | Request Payload  | Response Payload OK                       | Response Payload KO                                                                                                            |
|-------------|------------------|-------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------|
| /get-config | No payload needed | Returns the config loaded by the program  | ``` { "timestamp": "2024-11-01T13:50:12.911+00:00", "status": 500, "error": "Internal Server Error","path": "/get-config"} ``` |
| /validate   | ```{ "command": "A0", "key": 1234, "lmk": 4, "encoding": "V", "output": "J"}```              | ```{"valid": "true", "message": null }``` | ```{"valid": "true", "message": "$Property not valid" }```                                                                     |

All requests must include a custom header named `Api-Key`, the value can be set using the `confi.yaml` file 
(please read the Configuration section)

> [!WARNING]
> **This is just a demo and should not be used in production**

The idea is to validate jPOS <-> HSM messages by `POST`ing them to our `/validate` endpoint. 

To run this app you should have the following:
- JAVA JDK: 17.0.13-amzn
- gradle: 7.6.4

Then move into the app directory and execute:

```bash

gradle bootRun
```

### Configuration
> [!NOTE]
> For your convenience a `config.yaml.example` is provided so you can just rename it to
> `config.yaml` and replace the necessary values

This app offers the ability to be configured using the `src/main/resources/config.yaml` which includes
the following properties:

```yaml
rest:
  port: 8000
  apikey: sup3rs3cr3t
  # J for JSON, X for XML
  input: "J"
  # J for JSON, X for XML
  output: "J"
```

- The `port` sets the port where our app will listen.
- The `apikey` sets the key to validate every request.
- The `input` set the valid content type for the incoming payload
- The `output` set the output content type for response of our app.

### Running in docker

The prebuilt image is just a demo, please make sure to make the **necessary** changes before building your own images.

A `docker-compose.example.yaml` file is provided for your convenience with a prebuilt docker image that can be found in
dockerhub here: [https://hub.docker.com/r/jeop10/java-endpoint-test](https://hub.docker.com/r/jeop10/java-endpoint-test)

The default listening port is `8000` and the default api-key is `sup3rs3cr3t`. 

### Build docker images

In order to build the docker images first you must create the `*.jar`.
To do that you must execute the following command:

```bash
gradle bootJar
```

This will create a `*.jar` file inside `build/libs`

Finally, you can execute `docker build -t [IMAGE-NAME]` where `IMAGE-NAME` is the name of the image that you choose.

### Tests

This app have a test suite on `DemoApplicationTests`