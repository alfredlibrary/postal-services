Alfred - Postal Services API
=================
Hey! Did you buy a PS3 game and is waiting it to arrive at your home? Do you want to get informations about what is happening to your package? Where it is and so on?
Well, you can create your own application to track that informations. And if you will create your own application, you must use Alfred - Postal Services API.

Usage
------
Postal Services API is integrated with Brazilian Postal Services (Correios) and United States Postal Service (USPS). How to use it? It's simple like that:

public class MyClass {
	
	private Tracking tracking;
	
	public void testing() {
		tracking = TrackingFactory.getUSPS("myID", false);
		TrackingInfo info = tracking.track("CODE");
	}
	
}

With this code, you will be tracking an package sent from USPS. If you are in Brazil, you need a few changes in the code above:

public class MyClass {
	
	private Tracking tracking;
	
	public void testing() {
		tracking = TrackingFactory.getCorreios();
		TrackingInfo info = tracking.track("CODE");
	}
	
}

Easy!

License
------
Alfred - Postal Services API is licensed under LGPL version 3. In other words, you can use it in your commercial and open softwares.
