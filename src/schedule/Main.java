package schedule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class Main{
	
		public static void main(String[] args) throws ClassNotFoundException {
	
			class Group_scheduleComparator implements Comparator<Group_schedule> {
			    public int compare(Group_schedule gs1, Group_schedule gs2) {
			        return gs2.getRoom().getValue() - gs1.getRoom().getValue();
			    }
			}
			
			int[] sched = {1,2};
			
			DbHelper db = new DbHelper();
			Group_scheduleDAO gsDAO = new Group_scheduleDAO(db.connection);
			ScheduleDAO sDAO = new ScheduleDAO(db.connection);
			List<Summary> sumList = new ArrayList<Summary>();
			
			for(int schedule : sched)
			{
				List<Group_schedule> gs = gsDAO.getGroup_scheduleListByScheduleId(schedule);
				List<Group_schedule> groups = fillRooms(db, schedule, gs);
				Collections.sort(groups,new Group_scheduleComparator());
				Summary s = new Summary();
				s.setSchedule(sDAO.getSchedule(schedule));
				s.setGSList(groups);
				sumList.add(s);
				System.out.println("Schedule: " + schedule + "-------------");		
				for(Group_schedule g : groups) {
	
					String teacher = "";
					if(g.getGroupObject().getTeacher() != null)
					{
						teacher = g.getGroupObject().getTeacher();
					}
					
						System.out.println("Group: " + g.getGroupObject().getName() + " Room: " + g.getRoom().getName() + " Teacher: " + teacher);		
		        
				}
			}
			
			System.out.println("asd");
		}
		
		private static List<Group_schedule> fillRooms(DbHelper db, int schedule, List<Group_schedule> gs)
		{
			GroupDAO groupDAO = new GroupDAO(db.connection);
			List<Group> groupList = groupDAO.getGroupList(schedule);
			Collections.sort(groupList);
			
			for(int i = 0; i < gs.size(); i++)
			{
				gs.get(i).setGroupObject(groupDAO.getGroup(gs.get(i).getGroup()));
			}
			Collections.sort(gs);
			
			RoomDAO roomDAO = new RoomDAO(db.connection);
			List<Room> rooms = roomDAO.getRoomList();
			Collections.sort(rooms);
			
			int count = Math.max(gs.size(), rooms.size());
			for (int i = 0; i < count; i++)
			{
				
				if(i == rooms.size())
				{
					Room virtRoom = new Room();
					virtRoom.setCapacity(99);
					virtRoom.setValue(0);
					rooms.add(virtRoom);
				}
				else if(i == gs.size())
				{
					Group virtGroup = new Group();
					virtGroup.setCapacity(0);
					virtGroup.setValue(0);
					Group_schedule newGS = new Group_schedule();
					newGS.setGroupObject(virtGroup);
					gs.add(newGS);
				}
				Group_schedule g = gs.get(i);
				Room r = rooms.get(i);
				g.setRoom(r);
			}
			
			boolean flag = true;
			while(flag)
			{
				flag = false;
			for (int i = 0; i < gs.size(); i++)
				{
				Group_schedule r = gs.get(i);
				if(r.getRoom() == null) continue;
				
				for (int n = 0; n < gs.size(); n++)
				{
					Group_schedule g = gs.get(n);
					//if(g == null) continue;
					
					if(r.getRoom() == null) break;
					
					if(r.getGroupObject().getValue() < g.getGroupObject().getValue()) {
						if(g.getRoom() == null && r.getRoom().getCapacity() >= g.getGroupObject().getCapacity()){
							
							g.setRoom(r.getRoom());
							r.setRoom(null);
							flag = true;
							
						}else if(g.getRoom() != null && r.getRoom() != null){
							if(r.getRoom().getValue() > g.getRoom().getValue() && r.getRoom().getCapacity() >= g.getGroupObject().getCapacity() && 
									g.getRoom().getCapacity() >= r.getGroupObject().getCapacity()) 
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
			
			return gs;
		}
		
		
	
}
