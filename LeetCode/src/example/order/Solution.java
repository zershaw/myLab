package example.order;

class Solution {
    public int longestSubstring(String s, int k) {
        int n = s.length();
        return dfs(s,0,n - 1, k);
    }
    public int dfs(String s, int left,int right, int k){
        int[] cnt = new int[26];
        for(int i = left; i <= right; i++){
            cnt[s.charAt(i) - 'a']++;
        }
        // 如果存在某个字符 split，它的出现次数大于 0 且小于 k，则任何包含 split 的子串都不可能满足要求。
        char split = 0;
        for (int i = 0; i < 26; i++) {
            if(cnt[i] > 0 && cnt[i] < k){
                split = (char)(i + 'a');
            }
        }
        // 子字符串中所有字符都符合题设，返回字符串长度
        if (split == 0) {
            return right - left + 1;
        }
        //否则,从左向右遍历字符串
        int i = left;
        int res = 0;
        while (i <= right){
            while(i <= right && s.charAt(i) == split){
                i++;
            }
            // 遍历分段后的字符串
            int start = i;
            while(i <= right && s.charAt(i)!= split){
                i++;
            }
            int len = dfs(s,start,i-1,k);
            res = Math.max(res,len);
        }
        return  res;
    }
}