# Minecraft Mod Environment

This is a basic template for developing Minecraft mods.

## Requirements

- [Docker](https://www.docker.com/)
- Java JRE + JDK
- [Maven](https://maven.apache.org/)

## Config

You will want to change the following:

`pom.xml`
```xml
  <artifactId>PLUGIN_NAME</artifactId>
  <version>0.1</version>

  <name>PLUGIN_NAME</name>
```

`./src/main/resources/plugin.xml`
```
name: PLUGIN_NAME
```

`docker-compose.yml`
```
      - EULA=true
      - OP=MINECRAFT_NAME
```

## Run Server

To run the server, use the following command.

```bash
sudo docker-compose up
```

This will build the latest Spigot server and run on port `25565`. You can connect with your client.

## Run Plugin

To run the plugin, you will need to compile it using:

```bash
mvn install && mvn clean
```

On your client, run the command:

```minecraft
/reload
```