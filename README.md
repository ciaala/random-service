# random-service
This project contains a working example of a google app engine service  built on top of JavaSpark.

## App Engine
The app engine service used is the flexible enrivonment configured as a custom docker.
See 
- src/main/appengine/app.yaml 
- src/main/Docker/Dockerfile

## Maven
Is used to handle javaspark dependency and deploy by using two plugins.
- appengine-maven-plugin (the one used in the flexible environment)
- maven-assembly-plugin

## Service
The service gives the user the ability to generate a random value.
The service generates an image with the random value and other information.
The randomness is built to be unique through an identifier. 
- the identifier of the random 
- the number of time the random value has been viewed

### Request
GET format:
```
/random/:identifier/:random-range
```

Example:
> http://random-service.appspot.com/random/my-roll-dice/16

### Response
The response is a PNG image 256x256 pixels with a black background and three information in different location
- identifier ( top-left )
- random value ( center )
- views count ( bottom-right )

![Image generated](http://random-service.appspot.com/random/my-roll-dice/16)
