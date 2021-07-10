#### How to run:

**Commands to run inside project directory:**
> ```sbt "test; docker:publishLocal"``` - run modular test and build docker image locally

> ```docker run -p 8080:8080 wolt-assignment:0.1``` - expose application and use port forwarding for your host machine

> ```docker ps``` - make sure that service in UP status

You can upload your Json payload with info using the link below and **HTTP POST** method
```
localhost:8080/convert
```
Expecting results after uploading json (from assignment for example)
- **200**                      - if convertation was success you will get human readable representation
- **422 Unprocessable Entity** - when your json is invalid
- **400 Bad Request**          - when you did not pass validation of value (it should be > 0 && < 86400)

>> **If you dont have docker just run ```sbt run``` inside the project directory and call rest API as well**

####TASK 2.
Regarding to format I think that it is a little bit complicated,
Another possible format I see is to use 2 arrays of data, and store data only in minutes for each week

**Example:**
So we have 7 days in week and each day has 24 hours maximus minutes in day = 24 * 60 = 1440 minutes
<p>Maximum week value would be (24 * 7 * 60 - 1) = 10080 - 1 = 10079
<p>To find schedule where restaraunts are opened or closed we need to find similar indixes in both arrays and to undestand for which day this data is related
<p>To keep in mind the format will be easier to understand but from the code perspective
<p>It might be bot transparent how to calculate and client of API should know some details

- <b>$one_day_in_minutes</b> = 24 * 60 = 1440 minutes
- <b>$monday</b>             = from 0                       to $one_day_in_minutes
- <b>$tuesday</b>            = from $one_day_in_minutes     to $one_day_in_minutes * 2
- <b>$wednesday</b>          = from $one_day_in_minutes * 2 to $one_day_in_minutes * 3
- <b>$thursday</b>           = from $one_day_in_minutes * 3 to $one_day_in_minutes * 4
- <b>friday</b>              = from $one_day_in_minutes * 4 to $one_day_in_minutes * 5
- <b>$saturday</b>           = from $one_day_in_minutes * 5 to $one_day_in_minutes * 6
- <b>$sunday</b>             = from $one_day_in_minutes * 6 to $one_day_in_minutes * 7 - 1


```
Json payload: 
}
  open  : [
    $one_day_in_minutes     + 600,
    $one_day_in_minutes * 3 + 640,
    $one_day_in_minutes * 4 + 600,
    $one_day_in_minutes * 5 + 600,
    $one_day_in_minutes * 6 + 720
  ],
  close: [
    $one_day_in_minutes     + 1080,
    $one_day_in_minutes * 3 + 1080,
    $one_day_in_minutes * 5 + 60,
    $one_day_in_minutes * 6 + 60,
    $one_day_in_minutes * 6 + 1260,
  ] 
}
```

####What to improve:
- Add configs, for static information
- Add logback and loggers
- Add Swagger as a Tapir(OpenApi docs for http4s)
- Addition validation for input data
- Test not only for happy paths

