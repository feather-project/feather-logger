<h2>REST api</h2>

<h4>Register single or multiple logs</h4>
```html 
POST
http(s)://$HOST:$PORT/api/v1/logger/logs
```
```json
{
  "fileId": "fileName",
  "logs": [
    {
      "level": "info",
      "message": "An info message"
    },
    {
      "level": "WaRn",
      "message": "A warn message"
    },
    {
      "level": "ERROR",
      "message": "An error message"
    },
    {
      "level": "debUG",
      "message": "A debug message"
    },
    {
      "level": "test",
      "message": "A custom message"
    }
  ]
}
```

<h2>Websocket</h2>

```html 
ws(s)://$HOST:$PORT/ws/v1/connect
```

<h4>Websocket multiple logs creation payload</h4>
```json
{
    "fileId": "fileName",
    "request": "create.multiple",
    "content": {
        "logs": [
            {
                "level": "info",
                "message": "info message"
            },
            {
                "level": "debug",
                "message": "debug message"
            }
        ]
    }
}
```

<h4>Websocket single log creation payload</h4>
```json
{
    "fileId": "fileName",
    "request": "create.single",
    "content": {
        "log": {
            "level": "warn",
            "message": "warn message"
        }
    }
}
```

<h4>Websocket get all logs payload</h4>
```json
{
  "fileId": "fileName",
  "request": "get.all"
}
```