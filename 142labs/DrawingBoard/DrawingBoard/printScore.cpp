#include "printScore.h" 
  
/*Do not modify this code in any way*/
void printScore(vector<string> scores, string playerName) 
{ 
    string uplayer = playerName; for (int s = 0; s<uplayer.size(); uplayer[s] = toupper(uplayer[s]), s++); 
    string pname = "Bowler: " + uplayer+ "   "; 
    string slead; for (int s=0;s<pname.size();slead+=" ",s++); 
    const char C[] = { 201, 205, 187, 186, 200, 188, 182, 209, 203, 192, 179, 196, 202, 185, 202}; 
    if(scores.size() == 3) 
    { 
        //cout<<"Bowler: "<<playerName<<"\n\n"; 
        cout<<slead<<"Frame:"<<1<<"\n";  
        cout<<slead<<C[0]<<C[1]<<C[1]<<C[1]<<C[8]<<C[1]<<C[1]<<C[2]<<"\n"; 
        cout<<slead<<C[3]<<"  "<<scores[0]<<C[3]<<" "<<scores[1]<<C[3]<<"\n"; 
        cout<<pname<<C[3]<<"   "<<C[4]<<C[1]<<C[1]<<C[13]<<"\n"; 
        cout<<slead<<C[3]<<"  "<</*(scores[2]=="#"?" ":scores[2])*/scores[2]<<"   "<<C[3]<<"\n"; 
        cout<<slead<<C[4]<<C[1]<<C[1]<<C[1]<<C[1]<<C[1]<<C[1]<<C[5]; 
        cout << endl; 
    } 
    else
    { 
    if(scores.size()%3==1||scores.size()%3==0){ 
    if(scores.size() < 3){cout<<"Must have at least 1 complete frame stored in vector"<<endl; 
        return;} 
    for(int i = 0; i < scores.size(); i++) 
    { 
        if(scores[i].length()>3) 
        { 
            cout<<"Invalid Vector Format: ["<<scores[i]<<"] in index "<<i<<"."<<endl; 
            return; 
        } 
    } 
    int size = scores.size() / 3; 
    //cout<<"Bowler: "<<uplayer<<"\n\n"; 
    cout<<slead; 
    for(int i = 0; i < size; i++) 
    { 
        string s=i<10?" ":""; 
        cout<<"Frame:"<<i+1<<s;  
    } 
    cout<<"\n"<<slead; 
    for(int i = 0; i < size - 1; i++) cout<<C[0]<<C[1]<<C[1]<<C[1]<<C[8]<<C[1]<<C[1]<<C[2];           
    if(scores.size() % 3 == 1) cout<<C[0]<<C[1]<<C[8]<<C[1]<<C[8]<<C[1]<<C[1]<<C[2]; 
    else cout<<C[0]<<C[1]<<C[1]<<C[1]<<C[8]<<C[1]<<C[1]<<C[2];    
    cout<<"\n"<<slead; 
    for(int i = 0; i < scores.size()-4; i=i+3) cout<<C[3] << "  " << scores[i] << C[3]<<" "<< scores[i + 1] << C[3]; 
    if(scores.size() % 3 == 1) cout<<C[3] << scores[scores.size() - 4] << C[3] << scores[scores.size() - 3] << C[3]<<" " << scores[scores.size() - 2] << C[3] ; 
    else cout<<C[3] << "  " << scores[scores.size() - 3] << C[3]<<" "<< scores[scores.size() - 2] << C[3]; 
    cout<<"\n"<<pname; 
    for(int i = 0; i < size - 1; i++) cout<<C[3]<<"   "<<C[4]<<C[1]<<C[1]<<C[13]; 
    if(scores.size() % 3 == 1) cout<<C[3]<<" "<<C[4]<<C[1]<<C[14]<<C[1]<<C[1]<<C[13]; 
    else cout<<C[3]<<"   "<<C[4]<<C[1]<<C[1]<<C[13]; 
    cout<<"\n"<<slead; 
    for(int i = 2; i < scores.size() - 3; i=i+3) 
    { 
        if(scores[i].length() == 1) cout<<C[3]<<"   " << /*(scores[i]=="#"?" ":scores[i])*/scores[i] << "  "<< C[3]; 
        else if(scores[i].length() == 2) cout<<C[3]<<"  " << scores[i] << "  "<< C[3]; 
        else cout<<C[3]<<"  " << scores[i] << " "<< C[3]; 
    } 
    string s=scores[scores.size() - 1].size()==1?"   ":scores[scores.size() - 1].size()==2?"  ":" "; 
    cout<<C[3]<<"  " << scores[scores.size() - 1]/*(scores[scores.size() - 1]=="#"?" ":scores[scores.size() - 1])*/ <<s<< C[3]; 
    cout<<"\n"<<slead; 
    for(int i = 0; i < size; i++) cout<<C[4]<<C[1]<<C[1]<<C[1]<<C[1]<<C[1]<<C[1]<<C[5]; 
    cout<<"\n"; 
    return;} 
    else cout << "Incorrect Vector Size.  Must be a multiple of 3, or a multiple of 3 with 1 additional at the end for the extra ball in the final frame\n"; 
    } 
}