package schedule;

import java.util.Collections;
import java.util.List;


public class Main{
	
		public static void main(String[] args) throws ClassNotFoundException {
	
			int[] sched = {1,2};
			
			DbHelper db = new DbHelper();
			
			GroupDAO groupDAO = new GroupDAO(db.connection);
			List<Group> groupList = groupDAO.getGroupList();
			Collections.sort(groupList);
			
			RoomDAO roomDAO = new RoomDAO(db.connection);
			List<Room> rooms = roomDAO.getRoomList();
			Collections.sort(rooms);
			
			
			for (int i = 0; i < groupList.size(); i++)
			{
				Group g = groupList.get(i);
				if(i == rooms.size())
				{
					Room virtRoom = new Room();
					virtRoom.setCapacity(g.getCapacity());
					rooms.add(virtRoom);
				}
				Room r = rooms.get(i);
				r.setGroup(g);
			}
			
			boolean flag = true;
			while(flag)
			{
				flag = false;
			for (int i = 0; i < rooms.size(); i++)
				{
				Room r = rooms.get(i);
				if(r.getGroup() == null) continue;
				
				for (int n = 0; n < rooms.size(); n++)
				{
					Room g = rooms.get(n);
					//if(g == null) continue;
					
					if(r.getGroup() == null) break;
					
					if(r.value < g.value) {
						if(g.getGroup() == null && g.capacity >= r.getGroup().getCapacity()){
							
							g.setGroup(r.getGroup());
							r.setGroup(null);
							flag = true;
							
						}else if(g.getGroup() != null && r.getGroup() != null){
							if(r.getGroup().getValue() > g.getGroup().getValue() && 
									r.capacity >= g.getGroup().getCapacity() && g.capacity >= r.getGroup().getCapacity()) 
							{
								Group temp = r.getGroup();
								r.setGroup(g.getGroup());
								g.setGroup(temp);
								flag = true;
							}
							
						}
												
						
					}
				
					
				}
				
				}
			}
			
			for(Room r : rooms) {
				if(r.getGroup() != null) {
					
					System.out.println("Group: " + r.getGroup().getName() + " Room: " + r.name);	
				}else{
					System.out.println("Group: NULL Room: " + r.name);	
					
				}
			
	        
			}
			
	    
		}
	
}
