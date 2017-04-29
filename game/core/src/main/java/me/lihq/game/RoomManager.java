package me.lihq.game;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.utils.Array;

import me.lihq.game.models.Room;

/**
 * EXTENDED
 * Manager class for rooms. it initialises all the in game rooms with TiledMap files
 */
public class RoomManager
{
    private Array<Room> roomArray;
    private Room murderRoom;

    /**
     * NEW FIELDS
     */
    private Room roomWithSecretDoor;
    private Room secretRoom;

    /**
     * Constructs the map
     */
    public RoomManager(AssetLoader assetLoader)
    {
        roomArray = new Array<>();
        initialiseRooms(assetLoader);
    }

    /**
     * This function initialises all the rooms of the Ron Cooke Hub and their transitions
     */
    private void initialiseRooms(AssetLoader assetLoader)
    {
        for (TiledMap map : assetLoader.mapArray){
            roomArray.add(new Room(map, assetLoader.arrowAtlas));
        }


        //Assign the murder room
        murderRoom = roomArray.random();
        murderRoom.setMurderRoom(true);

        /*
        EXTENDED CODE START
         */

        //secret room init
        secretRoom = new Room(assetLoader.secretRoom, assetLoader.arrowAtlas);
        Array<Room> roomsWithSecretDoor = new Array<>();
        for (Room room : roomArray){
            //every tiled map has a new boolean property, true if there is a spot for secret door
            if (room.getTiledMap().getProperties().get("hasSecretDoor").equals(true)){
                roomsWithSecretDoor.add(room);
            }
        }
        roomWithSecretDoor = roomsWithSecretDoor.random();
        System.out.println("Secret room in " + roomWithSecretDoor.getName());

        secretRoom.getEntryArray().get(0).setConnectedRoomId(roomWithSecretDoor.getID());
        secretRoom.getExitArray().get(0).setConnectedRoomId(roomWithSecretDoor.getID());
        secretRoom.setLocked(true);

        roomWithSecretDoor.addSecretDoor();

        /*
        EXTENDED CODE END
         */
    }

    /**
     * This returns a room from the list based on the id.
     *
     * @param id - The ID of the room they request.
     * @return (Room) the corresponding room
     */
    public Room getRoom(int id)
    {
        for (Room room : roomArray) {
            if (room.getID() == id) return room;
        }

        if (secretRoom.getID() == id) return secretRoom;

        return null;
    }


    /**
     * Gets the rooms in the map
     *
     * @return (Array<Room>) List of rooms that the map initialised
     */
    public Array<Room> getRoomArray()
    {
        return roomArray;
    }

    public Room getMurderRoom() {
        return murderRoom;
    }

    public Room getSecretRoom() {
        return secretRoom;
    }

    public Room getRoomWithSecretDoor() {
        return roomWithSecretDoor;
    }
}
