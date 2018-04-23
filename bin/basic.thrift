namespace java design.thrift.server

    typedef i64 long
    
    service BasicAPI {
        void put(1:string key, 2:string value),
        list<string> get(1:string key),
	list<string> getByTime(1:string key, 2:long time),
	void delKey(1:string key),
	void delValue(1:string key, 2:string value),
	list<string> diff(1:string key, 2:long time1, 3:long time2),
    }
