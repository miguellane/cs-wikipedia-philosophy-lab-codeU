package com.flatironschool.javacs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;

import org.jsoup.select.Elements;

public class WikiPhilosophy {
	final static WikiFetcher wf = new WikiFetcher();
	public static void main(String[] args) throws IOException {

		int ignore = 0;
		List<String> urlList = new ArrayList<String>();
		String url = "https://en.wikipedia.org/wiki/Java_(programming_language)";
//		String url = "https://en.wikipedia.org/wiki/Programming_language";
//		String url = "https://en.wikipedia.org/wiki/Formal_language";
//		String url = "https://en.wikipedia.org/wiki/Mathematics";
//		String url = "https://en.wikipedia.org/wiki/Quantity";
//		String url = "https://en.wikipedia.org/wiki/Property_%28philosophy%29";
//		String url = "https://en.wikipedia.org/wiki/Philosophy";



		urlList.add(url);

		while(url != "NULL"){
			System.out.println(url);
			if(url.compareTo("https://en.wikipedia.org/wiki/Philosophy") == 0){
				System.out.println("Found Philosophy.");
				return;
			}
			Elements paragraphs = wf.fetchWikipedia(url);
			url = "NULL";
			paragraphs = paragraphs.not("i");
			paragraphs = paragraphs.not("em");
foundValidURL:		for(Element para: paragraphs ){
				Iterable<Node> iter = new WikiNodeIterable(para);
				for (Node node: iter) {
//					System.out.println(node);
					if(node.toString().contains("("))
						ignore++;
					if(node.toString().contains(")"))
						ignore--;
//					System.out.println(ignore);
					if (node.hasAttr("href") && ignore == 0) {
//						System.out.println(node);
						String temp = node.attr("href");
						if(temp.startsWith("/wiki/") && !urlList.contains(temp)){
							url = "https://en.wikipedia.org"+temp;
							urlList.add(url);
							break foundValidURL;
			}	}	}	}
//			url = "NULL";
		}
		System.out.println("Dead End.");
		System.out.println(urlList);
		return;
	}
}
