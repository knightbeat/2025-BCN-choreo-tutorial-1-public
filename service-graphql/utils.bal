import ballerina/log;
//import ballerina/os;
import ballerina/sql;
import ballerina/time;
import ballerinax/mysql;
import ballerinax/mysql.driver as _;

mysql:Client mysqlEp;

function init() returns error? {
    //int dbPort = check int:fromString(dbPortStr);
    log:printInfo("DB INFO: ", host = DB_HOST, user = DB_USERNAME, password = DB_PASSWORD, database = DB_NAME, port = DB_PORT);

    mysqlEp = check new (host = DB_HOST, user = DB_USERNAME, password = DB_PASSWORD, database = DB_NAME, port = DB_PORT);
}

# This function provides the available room types for a given date range and guest capacity
#
# + checkinDate - checkin date
# + checkoutDate - checkout date
# + guestCapacity - guest capacity
# + return - returns the available room types
function getAvailableRoomTypes(string checkinDate, string checkoutDate, int guestCapacity) returns RoomTypeData[]|error {
    time:Utc userCheckinUTC = check time:utcFromString(checkinDate);
    time:Utc userCheckoutUTC = check time:utcFromString(checkoutDate);
    sql:ParameterizedQuery sqlQuery = `SELECT rt.*
        FROM room_type rt
        WHERE rt.guest_capacity >= ${guestCapacity}
        AND EXISTS (
            SELECT r.*
            FROM room r
            WHERE r.type_id = rt.id
            AND NOT EXISTS (
                SELECT res.*
                FROM reservation res
                WHERE res.room_number = r.number
                AND res.checkin_date < ${userCheckinUTC}
                AND res.checkout_date > ${userCheckoutUTC}
            )
        )`;
    stream<RoomTypeData, sql:Error?> infoStream = mysqlEp->query(sqlQuery);

    RoomTypeData[] roomTypes = [];

    error? e = infoStream.forEach(function(RoomTypeData roomTypeData) {
        log:printInfo("RoomType: ", name= roomTypeData.name, capacity= roomTypeData.guest_capacity, price= roomTypeData.price);
        roomTypes.push(roomTypeData);
    });
    
    if e is error {
        log:printError("Error processing stream", 'error = e);
        return e;
    }
    return roomTypes;
}

function getAvailableRooms(string checkinDate, string checkoutDate, int guestCapacity) returns RoomData[]|error {
    time:Utc userCheckinUTC = check time:utcFromString(checkinDate);
    time:Utc userCheckoutUTC = check time:utcFromString(checkoutDate);
    sql:ParameterizedQuery sqlQuery = `SELECT r.number AS room_number,
            rt.name AS room_type,
            rt.guest_capacity,
            rt.price
        FROM room r
        JOIN room_type rt ON r.type_id = rt.id
        WHERE rt.guest_capacity >= ${guestCapacity}
        AND r.number NOT IN (
            SELECT room_number
            FROM reservation
            WHERE (checkin_date < ${userCheckoutUTC} AND checkout_date > ${userCheckinUTC})
        )
        ORDER BY r.number`;
    stream<RoomData, sql:Error?> infoStream = mysqlEp->query(sqlQuery);

    RoomData[] rooms = [];

    error? e = infoStream.forEach(function(RoomData roomData) {
        log:printInfo("Room: ", number= roomData.room_number, typename= roomData.room_type, price= roomData.price);
        rooms.push(roomData);
    });
    
    if e is error {
        log:printError("Error processing stream", 'error = e);
        return e;
    }
    return rooms;
}

configurable string DB_HOST = ?; //os:getEnv("DB_HOST");
configurable string DB_USERNAME = ?; //os:getEnv("DB_USERNAME");
configurable string DB_PASSWORD = ?; //os:getEnv("DB_PASSWORD");
configurable string DB_NAME =?; //os:getEnv("DB_NAME");
configurable int DB_PORT = ?;//os:getEnv("DB_PORT");

