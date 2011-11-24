Alfred - Postal Services API
=================
Hey! Did you buy a PS3 game and is waiting it to arrive at your home? Do you want to get informations about what is happening to your package? Where it is and so on?
Well, you can create your own application to track that informations. And if you will create your own application using the Java language, 
you should use Alfred - Postal Services API. :-) Look the next session to discover why!

Postal Services
-------------
At this moment, this API is integrated with Brazilian Postal Services (Correios - www.correios.com.br) and United State Postal Services (USPS - www.usps.com). To track
packages sent using Correios, you will only need the Tracking Code. If you need to use USPS, then you must do your registration at USPS site and get your UserID.
Look at the examples below to know how to use our API. 

Usage
------
How to use it? To track your package using USPS, it's simple like that:

	public class MyClass {
		
		private Tracking tracking;
		
		public void testing() {
			tracking = TrackingFactory.getUSPS("myID", false);
			TrackingInfo info = tracking.track("CODE");
			System.out.println(info.getStatuses().get(0).getDescription());
			System.out.println(info.getStatuses().get(0).getDate());
			System.out.println(info.getStatuses().get(0).getCity());
			System.out.println(info.getStatuses().get(0).getState());
		}
		
	}

Use the boolean parameter to tell to Alfred to use the USPS Test Servers or not. If "false", it will use Production USPS servers.
If you are in Brazil, you need a few changes in the code above:

	public class MyClass {
	
		private Tracking tracking;
	
		public void testing() {
			tracking = TrackingFactory.getCorreios();
			TrackingInfo info = tracking.track("CODE");
			System.out.println(info.getStatuses().get(0).getDescription());
			System.out.println(info.getStatuses().get(0).getDate());
			System.out.println(info.getStatuses().get(0).getCity());
			System.out.println(info.getStatuses().get(0).getState());
		}
		
	}

Easy!

CDI Integration
-------------
You can use this API with CDI too. If you want to know more informations, look our project "postal-services-cdi" (www.github.com/alfredlibrary/postal-services-cdi). 

License
------
Alfred - Postal Services API is licensed under LGPL version 3. In other words, you can use it in your commercial and open softwares.
