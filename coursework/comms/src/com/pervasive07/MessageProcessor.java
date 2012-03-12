package com.pervasive07;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MessageProcessor {
	
	SerialMsg message;
	int offset;
	long timestamp;
	int nodeID;
	int temp;
	int lux;
	boolean fire;
	
	public MessageProcessor(SerialMsg m, long time) {
		message = m ;
		offset = message.baseOffset();
		timestamp = time;
		nodeID = message.get_srcid();
		temp = message.get_temperature();
		lux = message.get_light();
		fire = (0 != message.get_fire());
		
		setup();
	}
	
	private void setup() {
	    JSONObject json = getJSON();
	    RestClient sendClient = new RestClient();
	    sendClient.sendJSON(json);
	}

	public JSONObject getJSON(){
		JSONObject json = new JSONObject();
		
		
		try{
		json.put("groupId", "7");
		json.put("key", "AWgbUdRae");
		json.put("groupName", "Keeley and friends");
		json.put("sensorData", getSensorIDList());
		}
		catch (JSONException e){
			e.printStackTrace();
		}
		return json;
	}

	private JSONArray getSensorIDList() {
		JSONArray sensorIDList = new JSONArray();
		JSONObject jsonInternalObject = new JSONObject();
		try{
			jsonInternalObject.put("sensorId", nodeToSensorID());
			jsonInternalObject.put("nodeId", nodeID);
			jsonInternalObject.put("timestamp", timestamp);
			jsonInternalObject.put("temp", temp);
			jsonInternalObject.put("lux", JSONObject.NULL);
		}
		catch (JSONException e){
			e.printStackTrace();
		}
		sensorIDList.put(jsonInternalObject);

		JSONObject jsonInternalObject2 = new JSONObject();
		try{
			jsonInternalObject2.put("sensorId", nodeToSensorID());
			jsonInternalObject2.put("nodeId", nodeID);
			jsonInternalObject2.put("timestamp", timestamp);
			jsonInternalObject2.put("temp", JSONObject.NULL);
			jsonInternalObject2.put("lux", lux);
		}
		catch (JSONException e){
			e.printStackTrace();
		}
		sensorIDList.put(jsonInternalObject2);
		
		return sensorIDList;
	}

	// Takes the nodeID and gives it a logical id of 0, 1 or 2
	public int nodeToSensorID(){
		// #TODO check these nodeIDs are right
		switch (nodeID) {
		case 25:
			return 0;
		case 26:
			return 1;
		case 27:
			return 2;
		default:
			return -1;

		}
	}

	public boolean getFire() {
		
		return fire;
	}

}