import java.util.*;
public class NFA {
	String start;
	Set<String> ends;
	Map<String, Map<Character, Set<String>>> transitions;
	
	NFA (String[] ss, String[] ts) {
		ends = new TreeSet<>();
		transitions = new TreeMap<>();
		
		for(String v: ss) {
			String[] pieces = v.split(",");
			if (pieces.length>1) {
				switch (pieces[1]) {
					case "S":
						start = pieces[0];
						break;
					case "E":
						ends.add(pieces[0]);
						break;
				}
			}
		}
		
		for(String e: ts) {
			String[] pieces = e.split(",");
			String from = pieces[0], to = pieces[1];
			if(!transitions.containsKey(from)) {
				transitions.put(from, new TreeMap<>());
			}
			
			for(int i=2;i<pieces.length;i++) {
				char c = pieces[i].charAt(0);
				
				if(!transitions.get(from).containsKey(c)) {
					transitions.get(from).put(c, new HashSet<>());
					transitions.get(from).get(c).add(to);
				}
				
				if(transitions.get(from).containsKey(c)) {
					transitions.get(from).get(c).add(to);
				}
			}
		}
		System.out.println("Start:"+start);
		System.out.println("end:"+ends);
		System.out.println("Transitions:"+transitions);
	}
	public boolean match(String s) {
		Set<String> prevStates = new TreeSet<>();
		prevStates.add(start);
		for (int i=0;i<s.length();i++) {
			char c = s.charAt(i);
			Set<String> nextStates = new TreeSet<>();
			
			for (String state: prevStates) {
				if(transitions.get(state).containsKey(c)) {
					nextStates.addAll(transitions.get(state).get(c));
				}
			}
			if(nextStates.isEmpty()) {
				return false;
			}
			prevStates = nextStates;
		}
		
		for(String state: prevStates) {
			if(ends.contains(state)) {
				return true;
			}
		}
		return false;
	}
	
	public void test(String name, String[] inputs) {
		System.out.println("\n***"+name+"***");
		for(String s: inputs) {
			System.out.println("  "+s+":"+match(s));
		}
	}
	
	
	public static void main(String[] args) {
		
		String[] ss2 = {"A,S",
						"B,E",
						"C"};
		String[] ts2 = {"A,A,+,-",
				
						"A,B,0,1,2,3,4,5,6,7,8,9",						
						"A,C,0,1,2,3,4,5,6,7,8,9",

						"C,C,0,1,2,3,4,5,6,7,8,9",
						"C,B,.,0,1,2,3,4,5,6,7,8,9",
						
						"B,B,0,1,2,3,4,5,6,7,8,9"						
						};
		NFA obj2 = new NFA(ss2, ts2);
		
		String[] test1 = {"20","5.77","+99","-5.78a"};
		obj2.test("Test Cases", test1);
		
	}

}
 
