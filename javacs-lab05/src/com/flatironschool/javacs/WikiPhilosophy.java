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

	/**
	 * Tests a conjecture about Wikipedia and Philosophy.
	 * 
	 * https://en.wikipedia.org/wiki/Wikipedia:Getting_to_Philosophy
	 * 
	 * 1. Clicking on the first non-parenthesized, non-italicized link
	 * 2. Ignoring external links, links to the current page, or red links
	 * 3. Stopping when reaching "Philosophy", a page with no links or a page
	 *    that does not exist, or when a loop occurs
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {

		// some example code to get you started

		String url = "https://en.wikipedia.org/wiki/Java_(programming_language)";

		Elements paragraphs = wf.fetchWikipedia(url);//gives you the paragraphs, need to get out and scan for links
		ArrayList<String> visited = new ArrayList<String>();
		ArrayList<Element> links = new ArrayList<Element>();
		Elements currLinks, currParents;
		boolean thing = false;
		boolean hasLinks = false;

		while(1==1){
			visited.add(url);
			if (url.equals("https://en.wikipedia.org/wiki/Philosophy"))
			{System.out.println(visited);
			return;}

			Element firstPara = paragraphs.get(0);

			Iterable<Node> iter = new WikiNodeIterable(firstPara);
			for (Node node: iter) 
			{
				//node = (Element)no;
				currLinks = node.select("a[href]");
				for (Element el: currLinks)
				{
					hasLinks = true;
					currParents = el.parents();
					for (Element par: currParents)
					{
						if (!par.tagName().equals("i") || !par.tagName().equals("em"))
							links.add(par);
					}
				}
			}

			if (links.isEmpty())
			{
				System.out.println("no links on this page, end");
				return;
			}
			for (Element link: links)
			{
				if (link.attr("abs:href") == url)
				{}
				else
				{
					url = link.attr("abs:href");
					return;
				}
			}
		}

	}
}


//take first eligible link, add to list of links
//elligible link- not italicized or bold, not a link to the current page

//fetch the url from that link or determine there are no links

//make sure url hasn't been visited, if it has end

//go to that url

//check if it's philosophy, if yes end, if no, do loop again

// the following throws an exception so the test fails
// until you update the code



