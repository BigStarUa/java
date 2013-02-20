package schedule;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.crypto.dsig.keyinfo.KeyValue;

public class Main{
	
		

		int[] schedule815 = new int[]{1,2,3,4,5,6,7,8,9,10};
		//DbHelper db;
		
		public static void main(String[] args) throws ClassNotFoundException {

			List<Room> rooms = XmlHelper.getRooms("xml/rooms.xml");
			Collections.sort(rooms);
			
			List<Group> groupList = XmlHelper.getGroups("xml/groups.xml", "1");
			Collections.sort(groupList);	
			
			
			DbHelper db = new DbHelper();
			
			GroupDAO groupDAO = new GroupDAO(db.connection);
			
			Group group = groupDAO.getGroup(1);
			group.setLevel(1);
			Group gr = new Group();
			gr.setName("Elementary");
			gr.setLevel(2);
			groupDAO.updateGroup(gr);
			List<Group> groupL = groupDAO.getGroupList();
			
			for(Group g : groupL) {
				System.out.println("name = " + g.getName());
				System.out.println("level = " + g.getLevel());
			}
			
			
			for (int i = 0; i < groupList.size(); i++)
			{
				Group g = groupList.get(i);
				Room r = rooms.get(i);
				
				r.setGroup(g);
				
			}

			
//			for (int i = 0; i < rooms.size(); i++)
//				{
//				Room r = rooms.get(i);
//				if(r.occupied == null) continue;
//				
//				for (int n = 0; n < rooms.size(); n++)
//				{
//					Room g = rooms.get(n);
//					//if(g == null) continue;
//					
//					if(r.value < g.value) {
//						if(g.occupied == null && g.capacity >= r.occupied.capacity){
//							
//							g.occupied = r.occupied;
//							r.occupied = null;
//							
//						}else if(g.occupied != null && r.occupied != null){
//							if(r.occupied.value >= g.occupied.value && 
//									r.capacity >= g.occupied.capacity && g.capacity >= r.occupied.capacity) {
//								
//								Group temp = r.occupied;
//								r.occupied = g.occupied;
//								g.occupied = temp;
//							}
//							
//						}
//												
//						
//					}
//				
//					
//				}
//				
//				}
			
			
			for(Room r : rooms) {
				if(r.getGroup() != null) {
					
					System.out.println("Group: " + r.getGroup().getName() + " Room: " + r.name);	
				}else{
					System.out.println("Group: NULL Room: " + r.name);	
					
				}
			
	        
			}
			
	    
		}
	
}
