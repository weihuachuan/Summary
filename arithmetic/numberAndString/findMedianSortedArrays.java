package numberAndString;

public class findMedianSortedArrays {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int n = nums1.length;
        int m = nums2.length;
        if(m<n){
            findMedianSortedArrays(nums2,nums1);
        }
        int LMax1=0,RMin1=0,LMax2=0,RMin2=0,low=0,high=2*n,c1=0,c2=0;
        while(low <=high){
            c1 = (low+high)/2;
            c2 = (m+n) - c1;
            LMax1 = (c1==0)? Integer.MIN_VALUE:nums1[(c1-1)/2];
            RMin1 = (c1==2*n)? Integer.MAX_VALUE:nums1[c1/2];
            LMax2 = (c2==0)? Integer.MIN_VALUE:nums2[(c2-1)/2];
            RMin2 = (c2==2*m)? Integer.MAX_VALUE:nums2[c2/2];
            if(LMax1 > RMin2){
                high = high -1;
            }else if(LMax2 > RMin1){
                low = low + 1;
            }else{
                break;
            }
        }
        return (Math.max(LMax1,LMax2)+Math.min(RMin1,RMin2))/2.0;
    }
}
