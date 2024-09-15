db.getSiblingDB("admin").createUser(
    {
        user: "admin",
        pwd: "admin",
        roles: [
            {
                role: "readWrite",
                db: "lotto-web"
            }
        ]
    }
)

db = db.getSiblingDB("lotto-web");

const winNumbers = [1, 2, 3, 4, 5, 6];
const winTicketId = "72da6e9d";

const lostNumbers = [1, 7, 8, 9, 10, 6];
const lostTicketId = "143hj187";

db.getCollection("resultResponse").insertOne({
    _id: winTicketId,
    _class: "pl.lotto.domain.resultannouncer.ResultResponse",
    createdDate: new ISODate("2024-07-27T11:01:00.000Z"),
    drawDate: new ISODate("2024-07-27T10:00:00.000Z"),
    hitNumbers: winNumbers,
    isWinner: true,
    numbers: winNumbers,
    wonNumbers: winNumbers
});

db.getCollection("player").insertOne({
    _id: winTicketId,
    _class: "pl.lotto.domain.resultchecker.Player",
    drawDate: new ISODate("2024-07-27T10:00:00.000Z"),
    hitNumbers: winNumbers,
    isWinner: true,
    numbers: winNumbers,
    wonNumbers: winNumbers
});

db.getCollection("ticket").insertOne({
    _id: winTicketId,
    _class: "pl.lotto.domain.numberreceiver.Ticket",
    drawDate: new ISODate("2024-07-27T10:00:00.000Z"),
    numbersFromUsers: winNumbers,
});

db.getCollection("winningNumbers").insertOne({
    _id: new ObjectId("66d8946f9911967864cd0785"),
    _class: "pl.lotto.domain.numbergenerator.WinningNumbers",
    drawDate: new ISODate("2024-07-27T10:00:00.000Z"),
    numbers: winNumbers,
});


db.getCollection("resultResponse").insertOne({
    _id: lostTicketId,
    _class: "pl.lotto.domain.resultannouncer.ResultResponse",
    createdDate: new ISODate("2024-07-27T11:01:00.000Z"),
    drawDate: new ISODate("2024-07-27T10:00:00.000Z"),
    hitNumbers: [1, 6],
    isWinner: false,
    numbers: lostNumbers,
    wonNumbers: winNumbers
});

db.getCollection("player").insertOne({
    _id: lostTicketId,
    _class: "pl.lotto.domain.resultchecker.Player",
    drawDate: new ISODate("2024-07-27T10:00:00.000Z"),
    hitNumbers: [1, 6],
    isWinner: false,
    numbers: lostNumbers,
    wonNumbers: winNumbers
});

db.getCollection("ticket").insertOne({
    _id: lostTicketId,
    _class: "pl.lotto.domain.numberreceiver.Ticket",
    drawDate: new ISODate("2024-07-27T10:00:00.000Z"),
    numbersFromUsers: lostNumbers,
});
