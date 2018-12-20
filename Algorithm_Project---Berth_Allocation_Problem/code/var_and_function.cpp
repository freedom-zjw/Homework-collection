#define GLOBALS
#include"header.h" 
bool OpenDataFile(){
	char addpre[50]="../../data/",addsuff[50]=".in",data_add[50];
	char write_add[50];
	int data_add_len;
	
	freopen("CON", "r", stdin);
	freopen("CON", "w", stdout);
	printf("please input the game number:\n"); 
	scanf("%s",datanum); 
	if (strcmp(datanum,"0") == 0) return false;
	
	memset(data_add, 0, sizeof(data_add));
	strcat(data_add, addpre);
	strcat(data_add, datanum);
	strcat(data_add, addsuff);
	freopen(data_add, "r", stdin);
	
	memcpy(write_add,data_add, sizeof(data_add));
	data_add_len = strlen(data_add);
	write_add[data_add_len - 2] = 'o';
	write_add[data_add_len - 1] = 'u';
	write_add[data_add_len] = 't';
	write_add[++ data_add_len] = '\0';
	freopen(write_add, "w", stdout);
	
	return true;
}
void Print_Ans(){
	rep(i, 1, n-1)
		printf("%d,%d;\n", ANS.position_to_start_docking[i], ANS.time_to_start_service[i]);
	printf("%d,%d\n", ANS.position_to_start_docking[n], ANS.time_to_start_service[n]);
	printf("total wait time: %d\n", ANS.total_waiting_time);
	printf("last departure time: %d\n", ANS.last_departure_time);
	printf("fx: %d\n", ANS.fx);
	freopen("CON", "w", stdout);
	printf("The ans has been printed.\n\n");
}
void Evaluate(permutation &n1){
	int x1=0,x2=0,x3=0;
	rep(i, 1, n){
		if (n1.ans.position_to_start_docking[i] == -1 && n1.ans.time_to_start_service[i] == -1)x1++; 
		else {
	 		x2 += n1.ans.time_to_start_service[i] - boat[i].a;
	 		if (x3 < n1.ans.time_to_start_service[i] + boat[i].t)
		 		x3 =  n1.ans.time_to_start_service[i] + boat[i].t;
		}
	}
	n1.ans.fx = w1*x1 + w2*x2 + w3*x3;
	n1.ans.total_waiting_time = x2;
	n1.ans.last_departure_time = x3;
}
void update(sol &n1, sol n2){
	if (n1.fx > n2.fx) n1 = n2;
}
/////////////////////贪心 
bool check(permutation &n1, int stb, int edb, int stt, int edt){
	rep(i, stb, edb)
		rep(j, stt, edt)
			if (n1.sta[i][j]) return false;
	return true;
}
void find_location(permutation &n1, int idx){
	
	idx = n1.array[idx];
	n1.ans.position_to_start_docking[idx] = -1;
	n1.ans.time_to_start_service[idx] = -1;
	
	rep(t, boat[idx].a, T-1){//枚举何时停 
		if (t+boat[idx].t-1 > T-1)break;
		
		rep(b, 0, L-1){//枚举哪个位置开始停 
			if (b+boat[idx].b-1 > L-1)break;
			
			if (check(n1, b, b+boat[idx].b-1, t, t+boat[idx].t-1)){//检查能否停 
				//能停则停 
				n1.ans.position_to_start_docking[idx] = b;
				n1.ans.time_to_start_service[idx] = t;
				
				rep(k, b, b+boat[idx].b-1)
					rep(z, t, t+boat[idx].t-1)
						n1.sta[k][z] = 1;
				return;
			}
			
		}
		
	}
}
void Greedy(permutation	 &n1){//对n1中指定的停靠顺序贪心寻找位置停靠 
	 memset(n1.sta, 0, sizeof(n1.sta));
	 rep(i, 1, n)
		 find_location(n1, i);
	 Evaluate(n1);
}
///////////////////////////////////

////////////////////////////////爆搜
void dfs(int x){//求全排列 
	if (x == n+1){
		Greedy(p[0]);
		update(ANS, p[0].ans);
	}
	rep(i, 1, n)
		if (!v[i]){
			v[i] = 1;
			p[0].array[x] = i;
			dfs(x+1);
			v[i] = 0;
		}
}
void violence_search(){
	memset(v, 0, sizeof(v));
	dfs(1);
}
///////////////////////////////// 

/////////////////////////////////模拟退火
double random(){
	return  rand() / (RAND_MAX);
}
void Simulate_Anneal(){
	double Ini_T = 1;       //初始温度
	double eps = 1e-30;         //终止条件
	double Dec = 0.999;       //降温系数 
    int u, v;
    for (double E = Ini_T; E > eps; E *= Dec){
    	rep(Times, 0, 6){
   			permutation temp = p[Times];
			u = 2 + rand()%(n-1);
			v = 2 + rand()%(n-1); 
			while (u == v){
				u = 2 + rand()%(n-1);
				v = 2 + rand()%(n-1); 
			}
			swap(temp.array[u], temp.array[v]);
			Greedy(temp);	
			update(ANS,temp.ans);
			if (temp.ans.fx < p[Times].ans.fx || 
				exp(-(temp.ans.fx - p[Times].ans.fx)/E) > random() )
				p[Times] = temp;
		}
	}
}
////////////////////////////////// 