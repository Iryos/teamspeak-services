# Teamspeak-Services

This Service adds to every Teamspeak User on your Teamspeak Server a Spacer Group if they not using Overwolf to aligne the Icons.\
It is checked when a user connects to the server and after a certain interval.\
By default the interval is 10 min. With the environment variable ```OVERWOLF_CHECK_INTERVAL``` the interval can be adjusted.

The Teamspeak group must be called ```Overwolf Spacer```

## Usage
The image is hosted on [Github Docker Registry](https://github.com/Iryos/teamspeak-services/pkgs/container/teamspeak-services)

Minimal docker-compose.yml example:
```yaml
version: '3'
services:
  teamspeak-services:
    image: ghcr.io/iryos/teamspeak-services:latest
    restart: unless-stopped
    environment:
      TEAMSPEAK_HOST: <CHANGEME>                                # Need to be set
      TEAMSPEAK_QUERY_PASSWORD: <CHANGEME>                      # Need to be set
```

Extended docker-compose.yml example: 
```yaml
version: '3'
services:
  teamspeak-services:
    image: ghcr.io/iryos/teamspeak-services:latest
    restart: unless-stopped
    environment:
      TEAMSPEAK_HOST: <CHANGEME>                                # Need to be set
      TEAMSPEAK_QUERY_USERNAME: <CHANGEME>                      # Defaults to serveradmin 
      TEAMSPEAK_QUERY_PASSWORD: <CHANGEME>                      # Need to be set
      TEAMSPEAK_NICKNAME: <CHANGEME>                            # Defaults to SpaceCorrector
      OVERWOLF_CHECK_INTERVAL: <CHANGEME>                       # Specified in Seconds / Defaults to 600 sec.
```
