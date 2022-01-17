# Simple Wallet

### Instruction

Let's imagine a lot of people send Bitcoins to your wallet every second from different countries and different time
zones. The amount of BTC and the time is represented by an abstract record that has the following structure:

```json 
{
    "datetime": "2019-10-05T14:48:01+01:00",
    "amount": 1.1 
} 
```

You don't want to withdraw it and you don’t have any plan to use it in the near future because you really believe that
BTC will keep growing. Recently you’ve decided to keep track of and show a history of your wallet’s wealth to everyone.
At this time you’ve already collected `1000 BTC`. You want to show the history of your wallet balance at the end of
each `hour` between the DateTime range.
(Warning: not a history of transaction amount at that time range)
The data you want to provide contains an array of data with the following structure:

```json
[
  {
    "datetime": "2019-10-05T13:00:00+00:00",
    "amount": 1000
  },
  {
    "datetime": "2019-10-05T14:00:00+00:00",
    "amount": 1001.1
  }
]
```

You decided to create a web API (`Graphql`,`REST`or`GRPC`) and use any frameworks/technologies/libraries that might help
you.

Your server should have 2 feature

1) Save Record Input example:

```json
   {
  "datetime": "2019-10-05T14:45:05+07:00",
  "amount": 10
}
```

Output:

```
{
// Any output what can be recognized as successful
}
```

2) Get history of your wallet balance at the end of each `hour` between two dates Input example:

```json
{
  "startDatetime": "2011-10-05T10:48:01+00:00",
  "endDatetime": "2011-10-05T18:48:02+00:00"
}
```

Output example:

```json
[
  {
    "datetime": "2019-10-05T13:00:00+00:00",
    "amount": 1000
  },
  {
    "datetime": "2019-10-05T14:00:00+00:00",
    "amount": 1001.1
  }
]
```
