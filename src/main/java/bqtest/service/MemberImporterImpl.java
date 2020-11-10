package bqtest.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
public class MemberImporterImpl implements MemberImporter {


    public Map<String, List<Member>> processFile(File inputMemberFile) throws IOException {

        List<Member> membersFromFile = importMembers(inputMemberFile);

        return splitMembersByState(membersFromFile);

    }


    private List<Member> importMembers(File inputFile) throws IOException {
        return Files.lines(inputFile.toPath())
                .map(line -> {
                	int index = 0;
                	int size = 12;
                	Member mem = new Member();
                	mem.setId(line.substring(index, index + size).trim());
                	index += size;
                	size = 25;
                	mem.setLastName(line.substring(index, index + size).trim());
                	index += size;
                	size = 25;
                	mem.setFirstName(line.substring(index, index + size).trim());
                	index += size;
                	size = 30;
                	mem.setAddress(line.substring(index, index + size).trim());
                	index += size;
                	size = 20;
                	mem.setCity(line.substring(index, index + size).trim());
                	index += size;
                	size = 4;
                	mem.setState(line.substring(index, index + size).trim());
                	index += size;
                	size = 5;
                	mem.setZip(line.substring(index, index + size).trim());
                	
                   //TODO implement here
                    return mem;
                }).collect(Collectors.toList());
    }


    private Map<String, List<Member>> splitMembersByState(List<Member> validMembers) {
    	Map<String, List<Member>> map = new HashMap<String, List<Member>>();
    	List<String> states = new ArrayList<String>();
    	
    	for (Member mem : validMembers)
    	{
    		if(!states.contains(mem.getState()))
    		{
    			states.add(mem.getState());
    		}
    	}
    	
    	for (String state : states)
    	{
    		List<Member> members = validMembers.stream()
    				.filter(x -> x.getState().equals(state))
    				.collect(Collectors.toList());
    		map.put(state, members);
    	}
        return map;
    }
}
