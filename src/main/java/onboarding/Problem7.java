package onboarding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Problem7 {
    static final int firstFriend = 0;
    static final int secondFriend = 1;
    static final int knowPoint = 10;
    static final int visitPoint = 1;
    static final int recommendNum = 5;

    static List<List<String>> friendsList;
    static Map<String, Integer> friendsPointMap= new HashMap<>();

    static String userName;

    public static List<String> solution(String user, List<List<String>> friends, List<String> visitors) {
        List<String> answer = Collections.emptyList();

        friendsList = friends;
        userName = user;

        givePoint(user, visitors);

        answer = getKey(sortList());

        System.out.println("list : " + friendsPointMap);
        return answer;
    }

    //특정 사람과 친구인 사람 구하기
    public static List<String> findUserFriends(String user) {
        List<String> userFriendsList = new ArrayList<>();
        String friends = "";

        for (int i = 0; i < friendsList.size(); i++) {
            if (user.equals(friendsList.get(i).get(firstFriend)))
                userFriendsList.add(friendsList.get(i).get(secondFriend));
            if (user.equals((friendsList.get(i).get(secondFriend))))
                userFriendsList.add(friendsList.get(i).get(firstFriend));
        }
        return userFriendsList;
    }

    //visitor UnsupportedOperationException error 발생해서 list를 복제함
    public static List<String> saveVisitorList(List<String> visitors)
    {
        List<String> visitorsList = new ArrayList<>();

        for (int i = 0; i < visitors.size(); i++)
            visitorsList.add(visitors.get(i));

        return visitorsList;
    }
    public static void givePoint(String user, List<String> visitors)
    {
        List<String> userFriendsList;
        Map<String, Integer> userPointMap;

        //사용자와 친구인 목록
        userFriendsList = findUserFriends(user);

        //친구의 친구 목록을 구하고 점수를 구함
        saveUserPoint(findFriendsOfFriend(userFriendsList), knowPoint);
        saveUserPoint(removeDuplication(saveVisitorList(visitors), userFriendsList), visitPoint);
    }
    //사용자와 친구인 친구의 목록을 구해서 점수를 주는 함수
    public static List<String> findFriendsOfFriend(List<String> userFriendsList)
    {
        int root = 0;
        List<String> friendOfFriendsList;

        //사용자와 친구인 친구의 목록
        friendOfFriendsList = findUserFriends(userFriendsList.get(root));
        for (int i = 1; i < userFriendsList.size(); i++)
            friendOfFriendsList.addAll(findUserFriends(userFriendsList.get(i)));

        //사용자와 친구인 목록은 삭제
        removeDuplication(friendOfFriendsList,userFriendsList);

        return friendOfFriendsList;
    }
    public static List<String> removeDuplication(List<String> basicList, List<String> keywordList)
    {
        for (int i = 0; i < basicList.size(); i++)
        {
            //사용자가 리스트에 있을 경우
            if (userName.equals(basicList.get(i)))
                basicList.remove(i);
            if (i == basicList.size())
                break;
            //사용자의 친구가 리스트에 있을 경우
            for (int j = 0; j < keywordList.size(); j++)
            {
                if (basicList.get(i).equals(keywordList.get(j)))
                {
                    basicList.remove(i);
                    break;
                }
            }
        }
        return basicList;
    }
    public static Map<String, Integer> saveUserPoint(List<String> userList, int point)
    {
        int tmp = point;
        String key = "";

        for (int i = 0; i < userList.size(); i++)
        {
            point = tmp;
            key = userList.get(i);
            if (friendsPointMap.containsKey(key))
                point = friendsPointMap.get(key) + point;
            friendsPointMap.put(key, point);
        }
        return friendsPointMap;
    }

    //value 로 key 찾기
    public static List<String> getKey(Integer[] valueList) {
        int length;
        List<String> recommendFriendsList = new ArrayList<>();

        length = Math.min(recommendNum, valueList.length);

        for (int i = 0; i < length; i++)
        {
            for (String key : friendsPointMap.keySet()) {
                if (valueList[i] == friendsPointMap.get(key)) {
                    if (recommendFriendsList.contains(key))
                        continue;
                    recommendFriendsList.add(key);
                    break;
                }
            }
        }
        return recommendFriendsList;
    }

    public static Integer[] sortList()
    {
        int cnt;
        List<Integer> sortCheckPoint = new ArrayList<>();

        Collection <Integer> values = friendsPointMap.values();
        //배열 생성
        Integer [] intArray = values.toArray(new Integer[0]);
        Arrays.sort(intArray, Collections.reverseOrder());

        for (int i = 0; i < intArray.length; i++)
        {
            cnt = 0;
            while (intArray[i] == intArray[cnt]) {
                cnt++;
            }
            i = cnt;
            sortCheckPoint.add(cnt);
        }

        System.out.println(sortCheckPoint);
        return intArray;
    }
}
