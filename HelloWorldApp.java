import java.util.ArrayList;

class HelloWorldApp {
    public static void main(String[] args) throws InterruptedException {
        Metro m = new Metro("AlmatyMetro", "Almaty");
        Station s1 = new Station("Moscow Station", "Almaty,8a");
        Station s2 = new Station("Sairan Station", "Almaty,13c");
        Station s3 = new Station("Alatau Station", "Almaty,16b");
        m.addStation(s1);
        m.addStation(s2);
        m.addStation(s3);
        Train t1 = new Train("Sfenix10");
        Train t2 = new Train("Sfenix11");
        m.addTrain(t1);
        m.addTrain(t2);
        m.ListTrains();
        Machinist mch = new Machinist("Ury", 100000, t1.name);
        t1.addEmployer(mch);
        Passanger p = new Passanger("Alias", 320);
        Passanger p2 = new Passanger("Andrew", 2620);
        Passanger p3 = new Passanger("Maximalium", 90);
        s1.addTicketWindow();
        t1.addPassanger(p);
        t1.addPassanger(p2);
        t1.ListPassangers();
        System.out.printf("%s has %d tg \n",p.name,p.cash);

        p.BuyTicket(s1.getTicketWindow());
        p3.BuyTicket(s1.getTicketWindow());
        System.out.printf("%s has %d tg \n",p2.name,p2.cash);
        p2.BuyTicket(s1.getTicketWindow());
        System.out.printf("%s has %d tg \n",p2.name,p2.cash);
        p2.BuyTicket(s1.getTicketWindow());
        System.out.printf("%s has %d tg \n",p2.name,p2.cash);

        t1.addPassanger(p);
        t1.addPassanger(p3);
        t1.addPassanger(p2);

        t1.ListPassangers();
        System.out.printf("%s has %d tg \n",p.name,p.cash);
        System.out.printf("%s's cash bank have %d tg\n",s1.getTicketWindow().workPlaceName,s1.getTicketWindow().cashBank);

        t1.startPathWay(m.getStaions());
    }
    
    public interface _Metro{
        public void addStation(Station s);
        public void addTrain(Train t);
    }
    public interface _Station{
        public void addEmployer(Person p);
        public void addTicketWindow();
    }
    public interface _Train{
        public void addPassanger(Passanger p);
        public void addEmployer(Machinist m);
        public void startPathWay(Station[] ss) throws InterruptedException;
    }
    public interface _Passanger{
        public void BuyTicket(TicketWindow tw);
    }
    public interface _Person{
        public String getName();
    }
    public interface _TicketWindow{
        public void SellTicket(Passanger p);
    }      
    public static class Metro implements _Metro{
        public String name;
        public String location;
        Station[] stations;
        ArrayList<Station> als;
        Train[] trains;
        int counter_s = 0;
        private int counter_t = 0;
        public int status=0;//0 = not working , 1 = on work

        public Metro(String n,String l){
            stations=new Station[10];
            trains= new Train[10];
            if(n.length()>1&&n.length()<20)
                this.name=n;
            if(l.length()>2&&l.length()<20)
                this.location=l;
        }
        @Override
        public void addStation(Station station) {
            stations[counter_s]=station;
            // als.add(station);
            counter_s++;
        }

        @Override
        public void addTrain(Train train) {
            trains[counter_t]=train;
            counter_t++;
        }
        public void ListTrains(){
            int index2=0;
            while(trains[index2]!=null){
                System.out.println(this.name + " has " + trains[index2].name + " train");
                index2++;
            }
        }
        String getName(){
            return name;
        }

        Station[] getStaions(){
            return stations;
        }
        public String GetLocarion(){
            return location;
        }

    }
    public static class Station implements _Station{
        public String name;
        public String address;
        Person[] emps;
        TicketWindow tw;
        int counter;
        public int status=0;//0 = not working , 1 = on work
        
        public Station(String n,String a){
            counter=0;
            if(n.length()>1&&n.length()<20)
                this.name=n;
            if(a.length()>2&&a.length()<20)
                this.address=a;
        }

        @Override
        public void addEmployer(Person p) {
            if(p!=null)
            emps[counter]=p;
        }

        @Override
        public void addTicketWindow() {
            TicketWindow tw = new TicketWindow(this.name + " Ticket Window ", 0);
            tw.workPlaceName=this.name;
            this.tw=tw;

        }

        public TicketWindow getTicketWindow(){
            return this.tw;
        }
        public String GetAddress(){
            return address;
        }

    }
    public static class Person implements _Person{
        private int id;
        static int counter;
        String name;
        String workPlace;
        static{
            counter=1;
        }
        Person(String name){
            this.id=counter;
            if(name.length()>0)
            this.name=name;
        }
        public String getName(){
            return name;
        }
        public String GetWorkPlace(){
            return workPlace;
        }
        
    }
    static class Machinist extends Person{
        private int salary;
        String workplace;
        Machinist(String name,int salary,String workplace) {
            super(name);
            if(workplace.length()>0)
            this.workplace=workplace;
            if(salary>0)
            this.salary=salary;
        }
        public void Drive(Train t,Station[] ss) throws InterruptedException {
            if(t!=null&ss!=null)
            t.startPathWay(ss);
        }
        public int GetSalaty(){
            return salary;
        }
        public String GetWorkPlace(){
            return workPlace;
        }
    }
    public static class Сashier extends Person{
        private int salary;
        String workplace;
        Сashier(String name,int salary,String workplace) {
            super(name);
            this.workplace=workplace;
            this.salary=salary;
        }
        public void BeOnTicketWindow(TicketWindow tw){
            if(tw!=null)
                tw.addСashier(this);;
        }
        public int GetSalaty(){
            return salary;
        }
        public String GetWorkPlace(){
            return workPlace;
        }
    }

    public static class TicketWindow implements _TicketWindow {
        Сashier[] cs;
        String workPlaceName;
        private int cashBank;
        int counter;
        public int ticketCost=50;
        
        public TicketWindow(String name,int initialCash){
            counter=0;
            cs=new Сashier[2];
            this.workPlaceName=name;
            if(initialCash>0)
                this.cashBank=initialCash;
            else this.cashBank=0;
        }
        public void addСashier(Сashier c){
            if(c!=null)
                cs[counter]=c;
                counter++;
        }
        @Override
        public void SellTicket(Passanger p){
            if(p!=null){
                if(!p.HaveTicket){
                    p.BuyTicket(this);
                }
            }
        }
        public int GetCashBank(){
            return cashBank;
        }
        public void SetTicketCost(int cost){
            if(cost>0)
                this.ticketCost=cost;
        }
        public void TicketSold(){
            cashBank+=ticketCost;
        }
        
    }

    public static class Passanger implements _Passanger{
        private int id;
        private int cash;
        String name;
        static int counter;
        public boolean HaveTicket;
        static{
            counter=1;
        }
        public Passanger(String name){
            this.id=counter++;
            this.name = name;
            this.cash=500;
        }
        public Passanger(String name,int cash){
            this.id=counter++;
            this.name = name;
            this.cash=cash;
        }
        @Override
        public void BuyTicket(TicketWindow tw) {
            if(tw!=null){
                if(!HaveTicket){
                    cash-=tw.ticketCost;
                    HaveTicket=true;
                    tw.TicketSold();
                }
            }
        }
        public void GetIntoTrain(Train t) {
            if(t!=null){
                if(HaveTicket){
                    t.addPassanger(this);
                }
            }
        }
        
        public int getCash(){
            return cash;
        }
        
    }
    public static class Train implements _Train{
        private int id;
        static int counter;
        String name;
        Station nextStation;
        Station currentStation;
        Machinist m;
        Passanger[] ps;
        static int index;
        static{
            counter = 1;
            index = 0;
        }
        public int status=0;//0 = not working , 1 = on work

        public Train(String n){
            ps=new Passanger[50];
            if(n.length()>1&&n.length()<20)
                this.name=n;
            this.id=counter++;
        }
        @Override
        public void addPassanger(Passanger p) {
            if(p!=null)
                if (p.HaveTicket) {
                    ps[index]=p;
                    index++;
                }
                
            
        }

        @Override
        public void addEmployer(Machinist m) {
            if(m!=null)
            this.m=m;

        }
        public int getId(){
            return id;
        }
        public void ListPassangers(){
            int index2=0;
            while(ps[index2]!=null){
                System.out.println(ps[index2].name + "is in Train "+this.name);
                index2++;
            }
        }
        @Override
        public void startPathWay(Station[] ss) throws InterruptedException {
            int wayId=0;
            if(m!=null){
                while (true) {
                    if(ss[wayId+1]!=null){
                        currentStation=ss[wayId];
                        wayId++;
                        nextStation=ss[wayId];
                        Thread.sleep(3000);
                        System.out.printf("Train: %s ||%s --> %s \n",this.name, currentStation.name,nextStation.name);
                        
                    }
                    else{
                        while (true) {
                            if(wayId>0){
                                currentStation=ss[wayId];
                                wayId--;
                                nextStation=ss[wayId];
                                
                                Thread.sleep(3000);
                                System.out.printf("Train: %s ||%s --> %s \n",this.name, currentStation.name,nextStation.name);
                            }
                            else 
                            {
                                wayId=0;
                                break;
                            }
                        }
                    }
                    
                }
            }
            else{
                System.out.println("Machinist isn't in train!!!");
            }

        }

    }
}
