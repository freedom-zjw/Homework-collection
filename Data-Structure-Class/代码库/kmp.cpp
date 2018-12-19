void getnext()  {  
    int i,j=0;  
    next[1]=0; 
    for(i=2;i<=len2;i++){  
        while(j>0 && mod[j+1]!=mod[i]) j=next[j];  
        if(mod[j+1]==mod[i]) j++;  
        next[i]=j;  
    }  
}  
int KMP(){  
    int i=1,j=0,k=0;  
    getnext();   
    while(i<=len1){  
        while(j>0 && mod[j+1]!=s[i]) j=next[j];  
        if(mod[j+1]==s[i]){  
            j++;  
            if(j==len2) k++;  
        }  
        i++;  
    }  
    return k;  
}  
