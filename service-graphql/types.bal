 public type NewReservationRequest record {
    string checkinDate;
    string checkoutDate;
    int rate;
    User user;
    string roomType;
};

public type Room record {|
    readonly int number;
    RoomTypeData 'type;
|};

public type RoomTypeData record {
    int id;
    string name;
    int guest_capacity;
    decimal price;
};

public type User record {
    string id;
    string name;
    string email;
    string mobileNumber;
};

public type RoomData record {
    int room_number;
    string room_type;
    int guest_capacity;
    decimal price;
};