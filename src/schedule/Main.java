package schedule;

import java.util.Collections;
import java.util.List;


public class Main{
	
		public static void main(String[] args) throws ClassNotFoundException {
	
			int[] sched = {1,2};
			
			DbHelper db = new DbHelper();
			
			for(int schedule : sched)
			{
				List<Group> groups = fillRooms(db, schedule);
				System.out.println("Schedule: " + schedule + "-------------");		
				for(Group g : groups) {
	
					String teacher = "";
					if(g.getTeacher() != null)
					{
						teacher = g.getTeacher();
					}
					
						System.out.println("Group: " + g.getName() + " Room: " + g.getRoom().getName() + " Teacher: " + teacher);		
		        
				}
			}
			
	    
		}
		
		private static List<Group> fillRooms(DbHelper db, int schedule)
		{
			GroupDAO groupDAO = new GroupDAO(db.connection);
			List<Group> groupList = groupDAO.getGroupList(schedule);
			Collections.sort(groupList);
			
			RoomDAO roomDAO = new RoomDAO(db.connection);
			List<Room> rooms = roomDAO.getRoomList();
			Collections.sort(rooms);
			
			int count = Math.max(groupList.size(), rooms.size());
			for (int i = 0; i < count; i++)
			{
				
				if(i == rooms.size())
				{
					Room virtRoom = new Room();
					virtRoom.setCapacity(99);
					virtRoom.setValue(0);
					rooms.add(virtRoom);
				}
				else if(i == groupList.size())
				{
					Group virtGroup = new Group();
					virtGroup.setCapacity(0);
					virtGroup.setValue(0);
					groupList.add(virtGroup);
				}
				Group g = groupList.get(i);
				Room r = rooms.get(i);
				g.setRoom(r);
			}
			
			boolean flag = true;
			while(flag)
			{
				flag = false;
			for (int i = 0; i < groupList.size(); i++)
				{
				Group r = groupList.get(i);
				if(r.getRoom() == null) continue;
				
				for (int n = 0; n < groupList.size(); n++)
				{
					Group g = groupList.get(n);
					//if(g == null) continue;
					
					if(r.getRoom() == null) break;
					
					if(r.getValue() < g.getValue()) {
						if(g.getRoom() == null && r.getRoom().getCapacity() >= g.getCapacity()){
							
							g.setRoom(r.getRoom());
							r.setRoom(null);
							flag = true;
							
						}else if(g.getRoom() != null && r.getRoom() != null){
							if(r.getRoom().getValue() > g.getRoom().getValue() && r.getRoom().getCapacity() >= g.getCapacity() && 
									g.getRoom().getCapacity() >= r.getCapacity()) 
							{
								Room temp = r.getRoom();
								r.setRoom(g.getRoom());
								g.setRoom(temp);
								flag = true;
							}
							
						}
												
						
					}
				
					
				}
				
				}
			}
			
			return groupList;
		}
	
}
