

import java.util.*;
import java.io.*;
public class Main {

    public static void main(String[] args) throws IOException{
        Scanner sc = new Scanner(System.in);
        int n, x, ans = 0;
        n = sc.nextInt();
        int [] heights = new int[n];
        Map<Integer, HashSet<Integer>> map = new HashMap<>();
        Map<Integer, Integer> firstOccurance = new HashMap<>();
        for(x = 0; x < n; x++)
        {
            heights[x] = sc.nextInt();
            if(firstOccurance.get(heights[x]) == null)
            {
                firstOccurance.put(heights[x], x);
            }
            HashSet<Integer> hs = map.getOrDefault(heights[x], new HashSet<>());
            hs.add(x - firstOccurance.get(heights[x]));
            map.put(heights[x], hs);
        }
        for(Integer i: map.keySet())
        {
            HashSet<Integer> hs = map.get(i);
            System.out.print(i + ": ")
            for(Integer u: hs)
            {
                System.out.print(u + " ");
                int temp = 1;
                if(u == 0)
                    continue;
                for(int j = 1; hs.contains(u * j); j++)
                {
                    temp++;
                }
                ans = Math.max(temp, ans);
            }

        }
        System.out.println(ans);
    }
}