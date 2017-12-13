import java.util.*;
import java.util.stream.DoubleStream;

public class Rules {


    public static void main(String[] args) {
    int event_number = 5000;
    Rules tester = new Rules();
    String iv_event;
    String[] iv_actions;
    Random random = new Random();
    String[] actions = {"a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9"};
    String[] events = {"e1", "e2", "e3", "e4", "e5", "e6"};
    System.out.println("Complete Rule Set: ");
    HashMap<String, HashMap<String, String[]>> rules = new HashMap<String, HashMap<String, String[]>>();
    HashMap<String, String[]> event = new HashMap<>();
    //event.put("e1", actions);
    rules.put("r1", event);
    //System.out.println(Arrays.toString(rules.get("r1").get("e1")));
    //System.out.println(Arrays.toString(actionGen(actions, random.nextInt(6) + 1)));
    //System.out.println(eventGen(events));
    /** Test Rules
    String[] t_action1 = {"a1","a2","a3"};
    String[] t_action2 = {"a2", "a4", "a5"};
    String[] t_action3 = {"a1", "a3", "a4", "a6"};
    String[] t_action4 = {"a1","a3"};
    String[] t_action5 = {"a1","a3"};
    Rules tester = new Rules();
    tester.insert(t_action1);
    tester.insert(t_action2);
    tester.insert(t_action5);
    System.out.println(tester.search(t_action4)); **/
    for(int i=0;i<event_number;i++) {
//        iv_event = Rules.eventGen(events);
//        iv_actions = Rules.actionGen(actions, (random.nextInt(8)));

        event = ruleAdder();
        rules.put("r" + i ,event);
    }
    List<String> dict_entry = new ArrayList<String>();
    for (int b =0; b<event_number;b++) {
        dict_entry = tester.split_and_insert(ruleSplit(rules,b));
        System.out.println(("rule " + b) + dict_entry);
        System.out.println(("checking if rule " + b +" is already in trieNode: (Redundancy) ") + tester.search(dict_entry));
        tester.insert(dict_entry);
        System.out.println(("check after adding rule: ") + tester.search(dict_entry));
    }

    /*for(int i=0;i<event_number;i++)
        System.out.println(i +1 + " " +events[random.nextInt(5)]);*/

    /**
     * uncomment below to print ruleset
     * **/

    /*for (int a = 0; a<event_number;a++) {
        System.out.println("Rule " +( a +1 )+ ": " +Arrays.toString(rules.get("r" + a).get("e1")));
    }*/

    List<String> model = new ArrayList<String>();
    //System.out.println(rules.get());
    for (HashMap.Entry entry : rules.entrySet()) {
        //System.out.println(entry.getKey());
        model.add((String)entry.getKey());
    }
    System.out.println(model);
    System.out.println(ruleSplit(rules,0));

    //System.out.println(rules.get("r" + 1).hashCode() );



    }

    private class TrieNode {
        Map<String, TrieNode> children;
        boolean endOfactions;
        public TrieNode() {
            children = new HashMap<>();
            endOfactions = false;
        }
    }

    private final TrieNode root;
    public Rules() {
        root = new TrieNode();
    }
    public List<String> split_and_insert(HashMap<String, String[]> hashMap) {
        List<String> event_action_list = new ArrayList<>();
        for (HashMap.Entry entry : hashMap.entrySet()) {
            //System.out.println(entry.getKey());
            String event = (String)entry.getKey();
            String[] actions = (String[])entry.getValue();
            event_action_list.add(event);
            for (int i=0; i<actions.length; i++) {
                event_action_list.add(actions[i]);
            }
        }
        return event_action_list;

    }
    public void insert(List<String> word) {
        TrieNode current = root;
        for (int i = 0; i < word.size(); i++) {
            String ch = word.get(i);
            TrieNode node = current.children.get(ch);
            if (node == null) {
                node = new TrieNode();
                current.children.put(ch, node);
            }
            current = node;
        }
        //mark the current nodes endOfactions as true
        current.endOfactions = true;
    }
    public boolean search(List<String> word) {
        TrieNode current = root;
        for (int i = 0; i < word.size(); i++) {
            String ch = word.get(i);
            TrieNode node = current.children.get(ch);
            //if node does not exist for given char then return false
            if (node == null) {
                return false;
            }
            current = node;
        }
        //return true of current's endOfactions is true else return false.
        return current.endOfactions;
    }
    public static HashMap<String, String[]> ruleSplit (HashMap<String, HashMap<String, String[]>> arg1, int arg2) {
        HashMap<String, String[]> evRule;
        evRule = arg1.get("r" + arg2);
        return evRule;
    }

    public static String[] actionGen (String[] arg1, int arg2) {
        Random random = new Random();
        String[] actionList;
        actionList = new String[arg2];
        for(int i=0;i<arg2;i++)
            actionList[i] = arg1[random.nextInt(8)];

        return actionList;
    }

    public static String eventGen (String[] arg1){
        Random random = new Random();
        String event;
        event = arg1[random.nextInt(6)];
        return event;
    }

    public static HashMap<String, String[]> ruleAdder () {
        String[] actions = {"a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9"};
        String[] events = {"e1", "e2", "e3", "e4", "e5", "e6"};
        HashMap<String, String[]> rule = new HashMap<>();
        Random random = new Random();
        String iv_event;
        String[] iv_actions;
        iv_event = Rules.eventGen(events);
        iv_actions = Rules.actionGen(actions, (random.nextInt(actions.length) +1 ));
        rule.put(iv_event, iv_actions );
        return rule;
    }



}