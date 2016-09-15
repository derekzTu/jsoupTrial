//have to compile with jsoup jar

//relevant Java standard library imports
import java.util.List;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import javax.imageio.ImageIO;














//Relevant jsoup imports
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class imageDownload {

	//urls
	private static String url = null;
	private static final String googleUrl = "https://www.google.com/search?tbm=isch&q=";

	private static String bingUrl = "http://www.bing.com/images/search?q=";	
	private static String bingExtension= "&first=";

	private static String baiduUrl = "http://image.baidu.com/search/index?tn=baiduimage&word=";
	private static String baiduExtension = "&pn=";

	private static String imagenetUrl = "http://image-net.org/search?q=";
	private static String imageNetUrlList = "http://image-net.org/api/text/imagenet.synset.geturls?wnid=";

	private static String directoryName ="C:\\Users\\Derek\\Desktop\\dlimage";



	//List of possible agents
	private static List<String> listOfAgents = new ArrayList<>();
	private static String edge ="Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/42.0.2311.135 Safari/537.36 Edge/12.246";
	private static String firefox ="Mozilla/5.0 (Windows NT 6.1; WOW64; rv:40.0) Gecko/20100101 Firefox/40.1";
	private static String mozilla ="Mozilla/5.0 (Windows; U; Windows NT 6.1; rv:2.2) Gecko/20110201";
	private static String opera ="Opera/9.80 (X11; Linux i686; Ubuntu/14.10) Presto/2.12.388 Version/12.16";
	private static String safari ="Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_3) AppleWebKit/537.75.14 (KHTML, like Gecko) Version/7.0.3 Safari/7046A194A";
	private static String chrome ="Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36";
	private static String chromeplus ="Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US) AppleWebKit/532.2 (KHTML, like Gecko) ChromePlus/4.0.222.3 Chrome/4.0.222.3 Safari/532.2";
	private static String AOL ="Mozilla/4.0 (compatible; MSIE 8.0; AOL 9.7; AOLBuild 4343.27; Windows NT 5.1; Trident/4.0; .NET CLR 2.0.50727; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729)";
	private static String firebird ="Mozilla/5.0 (Windows; U; Windows NT 6.1; x64; fr; rv:1.9.2.13) Gecko/20101203 Firebird/3.6.13";
	private static String galaxy ="Galaxy/1.0 [en] (Mac OS X 10.5.6; U; en)";

	//checks to see if non-null
	private static int urlLength = 10;
	//checks to decide which websites to look at
	//what the url will end up being
	private static String source = null;
	//give jsoup enough time to parse
	private static final int aLongTime = 2000000000;
	private static long seed = (System.nanoTime()-System.currentTimeMillis());
	private static int uaNum =10;
	private static int num =61;//10

	private static Document doc = null;

	private static Elements images = null;

	//private static String[] websites = {"google","bing","imagenet","a"};
	//private static String manual = "a";

	private static int count = 0;
	private static int bingFilter = 150;
	private static Random random;
	private static String agent = null;
	private static List<String> listOfHashcodes= new LinkedList<>();
	private static String wnidCheck = "wnid=";

	public static void main(String[] args) throws IOException, InterruptedException {


		String choice = null;
		/*
		Scanner file = new Scanner(System.in);
		//create the directory
		createDirectory(file);


		//Number of images to be gotten
		System.out.println("Please enter the amount of images desired:");
		num = file.nextInt();

		//manually enter url vs automatically retrieve url
		System.out.println("Would you like to manually put in a url?y\\n");
		manual = file.nextLine();
		while((!(manual.equals("y")))&&(!(manual.equals("n")))){

			manual = file.nextLine();

		}
		 */
		num = Integer.parseInt(args[0]);
		String manual = args[1];
		choice = args[2];
		String input = args[3];
		/*
		if(manual.equals("n")){
			//Choose a website
			System.out.println("Please enter which website you would like to use from the "
					+ "following list(a for automatic)");
			for(int i =0; i<websites.length;i++){
				System.out.print(" " + websites[i] + ",");
			}
			System.out.println();
			choice = file.nextLine();
		}
		 */
		//	while (file.hasNextLine()){

		//	System.out.println("Please choose the query term");
		//	String input = file.nextLine();


		//google only possible 80
		if(choice.equals("google")){

			TimeUnit.SECONDS.sleep(20);
			getGoogleUrl(input);
			System.out.println(agent);
			documentParse();
			parseImages("img");
			googleDownload(images);


		}
		//bing ~1000
		//may download duplicates
		else if(choice.equals("bing")){
			while(num>0){
				TimeUnit.SECONDS.sleep(20);

				getBingUrl(input);
				documentParse();

				parseImages("img");
				bingDownload(images);
			}

		}

		else if(choice.equals("imagenet")){
			while(num>0){
				List<String> listOfwnids = new LinkedList<>();
				getImagenetUrl(input);
				documentParse();

				parseImages("a");
				imagenetUrlgetId(images,listOfwnids);
				List<String> downloads = imagenetUrls(listOfwnids);
				imagenetDownload(downloads);

			}
		}

		//for volume
		else{ //automatic
			System.out.println("Downloading from google");
			getGoogleUrl(input);

			documentParse();

			parseImages("img");
			googleDownload(images);

			while(num>0){
				
				TimeUnit.SECONDS.sleep(20);
				List<String> listOfwnids = new LinkedList<>();
				getImagenetUrl(input);
				documentParse();

				parseImages("a");
				imagenetUrlgetId(images,listOfwnids);
				List<String> downloads = imagenetUrls(listOfwnids);
				imagenetDownload(downloads);
			}
		}

		//end of while loops
	//}
	System.out.println("Done!");
	//file.close();
	//end of main
}


private static  boolean isServerReachable(String url) throws IOException, InterruptedException{
	Runtime runtime = Runtime.getRuntime();
	Process proc = runtime.exec("ping " + url); //<- Try ping -c 1 www.serverURL.com
	int mPingResult = proc.waitFor();
	if(mPingResult == 0){
		return true;
	}else{
		return false;
	}
}

private static void imagenetDownload(List<String> downloads) throws IOException, InterruptedException {
	for(int i=0;i<downloads.size();i++){
		if(isServerReachable(downloads.get(i))){

			URLConnection imageUrl = new URL(downloads.get(i)).openConnection();
			System.out.println("agent");
			imageUrl.addRequestProperty("User-Agent",agent);
			imageUrl.setRequestProperty("User-Agent",agent);

			imageUrl.connect();

			System.out.println(imageUrl.getInputStream());
			BufferedImage img = ImageIO.read(imageUrl.getInputStream());

			File outputfile = new File(directoryName+"\\" +imageUrl.hashCode()+".png");
			String hash = Integer.toString(imageUrl.hashCode());

			if(!(listOfHashcodes.contains(hash))){
				listOfHashcodes.add(hash);
				if(img!=null){
					ImageIO.write(img,"png",outputfile);
					num--;
					count++;
				}
			}
		}
	}
}


private static List<String> imagenetUrls(List<String> listOfwnids) throws IOException {
	List<String> urls = new ArrayList<>();
	for(int i =0;i<1;i++){
		url = imageNetUrlList+listOfwnids.get(i);
		documentParse();
		String body = doc.body().text();
		while(body.length()>0){
			int index = body.indexOf(" ");
			if(index<0){
				urls.add(body);
				body = "";
			}
			else{
				String singleUrl = body.substring(0,index);
				body = body.substring(index+1);
				urls.add(singleUrl);
			}
		}
	}
	return urls;
}


//gets the link
private static void imagenetUrlgetId(Elements images2,List<String> ids) {
	for(Element image:images){	
		source=image.attr("href");
		wnid(source,ids);
	}
}


//gets the wnid from the link
private static void wnid(String src,List<String>ids) {
	if(src.contains(wnidCheck)){
		int index = src.indexOf(wnidCheck);
		String id = src.substring(index+wnidCheck.length());

		if(!(ids.contains(id))){
			ids.add(id);
		}
	}
}

private static void documentParse() throws IOException{
	random = new Random(seed);
	int rand = random.nextInt(uaNum);
	addAgents();
	agent = listOfAgents.get(rand);
	doc = Jsoup.connect(url).userAgent(agent).timeout(aLongTime).get();

}
private static void parseImages(String tag){

	images = doc.getElementsByTag(tag);


}

private static void addAgents() {
	//add the agents
	listOfAgents.add(edge);
	listOfAgents.add(firefox);
	listOfAgents.add(mozilla);
	listOfAgents.add(chrome);
	listOfAgents.add(chromeplus);
	listOfAgents.add(galaxy);
	listOfAgents.add(firebird);
	listOfAgents.add(AOL);
	listOfAgents.add(opera);
	listOfAgents.add(safari);	


}
private static void getGoogleUrl(String in) {
	url = googleUrl+in;

}
private static void getBingUrl(String in){
	url = bingUrl + in+bingExtension+count;
}

private static void getImagenetUrl(String in) {
	url = imagenetUrl + in;
}
/*
	private static void getBaiduUrl(String in){
		url = baiduUrl + in+baiduExtension+count;
	}
 */
private static void createDirectory(Scanner file) {
	//creates the directory


	System.out.println("Please specify a directory to save to");

	//Constantly loops until a directory is made
	while(file.hasNextLine()){

		//the name of the directory as specified by the user
		directoryName = file.nextLine();
		//creates it as a file//
		File directory = new File(directoryName);
		boolean bool = false;	

		//check to see if directory already exists
		if(!directory.exists()){

			//if directory does not exist create it
			try{
				System.out.println("Directory made");
				directory.mkdir();
				bool = true;
			}
			catch(SecurityException se){	
			}

		}

		else{
			//if the directory exists ask them if they want to overwrite it
			System.out.println("Directory already exists, overwrite?Y/N");

			String answer = file.nextLine();


			if(answer.equals("Y")||answer.equals("y")){
				//overwrite the directory
				try{
					System.out.println("Directory made");
					directory.mkdir();
					bool = true;
				}
				catch(SecurityException se){	
				}
			}
		}
		//break out if directory has been made
		if(bool) break;
		//else continue until directory has been made
		System.out.println("Please specify a directory to save to");

	}
}
private static void googleDownload(Elements images) throws IOException {
	for(Element image:images){
		if(num>0){
			//get the url for the image
			if(agent.equals(chrome)){
				source = image.attr("abs:data-src");
			}
			else{
				source=image.attr("abs:src");
			}
			//download the image
			if(source.length()>urlLength){

				URL imageUrl = new URL(source);


				BufferedImage img = ImageIO.read(imageUrl);


				File outputfile = new File(directoryName+"\\" +imageUrl.hashCode()+".png");
				String hash = Integer.toString(imageUrl.hashCode());

				if(!(listOfHashcodes.contains(hash))){

					listOfHashcodes.add(hash);
					ImageIO.write(img,"png",outputfile);
					num--;
					count++;
				}

			}
		}
		else{
			break;
		}
	}

}
private static void bingDownload(Elements images) throws IOException {
	for(Element image:  images){
		if(num>0){
			//get the url for the image
			source = image.attr("abs:src");
			if (!(source.length()>urlLength)){
				source = image.attr("abs:src2");
			}

			//download the image
			if(source.length()>urlLength){
				URL imageUrl = new URL(source);
				BufferedImage img = ImageIO.read(imageUrl);
				String check = image.attr("height");
				if (check.length()>0){
					Integer height =Integer.parseInt(image.attr("height"));
					if(height>bingFilter){
						File outputfile = new File(directoryName+"\\" +imageUrl.hashCode()+count+".png");

						ImageIO.write(img,"png",outputfile);
						num--;
						count++;
					}
				}
			}
		}
		else{
			break;
		}
	}

}
/*private static void baiduDownload(Elements images2) throws IOException {

		for(Element image:  images){
			if(num>0){

				//get the url for the image
				source = image.attr("src");


				//download the image
				/*if(source.length()>urlLength){
					URL imageUrl = new URL(source);
					BufferedImage img = ImageIO.read(imageUrl);
					String check = image.attr("height");
					if (check.length()>0){
						Integer height =Integer.parseInt(image.attr("height"));
						if(height>bingFilter){
							File outputfile = new File(directoryName+"\\" +imageUrl.hashCode()+count+".png");

							ImageIO.write(img,"png",outputfile);
							num--;
							count++;
						}
					}
				}
			}
			else{
				break;
			}
		}

	}*/


//end of class	
}
