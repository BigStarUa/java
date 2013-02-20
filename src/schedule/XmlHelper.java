package schedule;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XmlHelper {

	Document doc;
	List<Group> groupList;

	public XmlHelper() {

				
	}
	
	public static List<Group> getGroups(String path, String schedule_id) {
		
		List<Group> groupList = new ArrayList<Group>();
		
			try {
			
			File fXmlFile = new File(path);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			
			//optional, but recommended
			//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
			doc.getDocumentElement().normalize();
			
			NodeList nList = doc.getElementsByTagName("group");
			
			for (int temp = 0; temp < nList.getLength(); temp++) {
				 
				Node nNode = nList.item(temp);
		  
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					
					
		 
					Element eElement = (Element) nNode;
					//if(eElement.getElementsByTagName("schedule").item(0).getTextContent() == schedule_id){
						//System.out.println("Group id : " + eElement.getAttribute("id"));
						String name = eElement.getElementsByTagName("name").item(0).getTextContent();
						int level = Integer.parseInt(eElement.getElementsByTagName("level").item(0).getTextContent());
						int capacity = Integer.parseInt(eElement.getElementsByTagName("capacity").item(0).getTextContent());
						int studentAge = Integer.parseInt(eElement.getElementsByTagName("studentAge").item(0).getTextContent());
						int teacher = Integer.parseInt( eElement.getElementsByTagName("teacher").item(0).getTextContent());
						int value = Integer.parseInt(eElement.getElementsByTagName("value").item(0).getTextContent());
						
						//Group group = new Group(name, level, capacity, studentAge, teacher, new int[] {1,2,3}, value);
						//groupList.add(group);
					//}
				}
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
		return groupList;
	}
	
	public static List<Room> getRooms(String path) {
		
		List<Room> roomList = new ArrayList<Room>();
		
			try {
			
			File fXmlFile = new File(path);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			
			//optional, but recommended
			//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
			doc.getDocumentElement().normalize();
			
			NodeList nList = doc.getElementsByTagName("room");
			
			for (int temp = 0; temp < nList.getLength(); temp++) {
				 
				Node nNode = nList.item(temp);
		  
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					
					
		 
					Element eElement = (Element) nNode;
		 
					//System.out.println("Room id : " + eElement.getAttribute("id"));
					String name = eElement.getElementsByTagName("name").item(0).getTextContent();
					int value = Integer.parseInt(eElement.getElementsByTagName("value").item(0).getTextContent());
					int capacity = Integer.parseInt(eElement.getElementsByTagName("capacity").item(0).getTextContent());
					
					Room room = new Room(name, value, capacity);
					roomList.add(room);
				}
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
		return roomList;
	}
	
	
}
