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
	private static ArrayList<String> visited = new ArrayList<String>();

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
		wikiPhil(url);
	}


	public static void wikiPhil(String url) throws IOException
	{
		Element link;
		for(int i = 0; i < 100; i++)
		{
			Elements paragraphs = wf.fetchWikipedia(url);
			if(visited.contains(url))
			{
				return; //already visited
			}
			else
			{
				visited.add(url);
			}
			link = firstLink(paragraphs);
			if (link == null)
			{
				return; //no links
			}

			System.out.print("Link: " + link.text());
			url = link.attr("abs:href");

			if (url.equals("https://en.wikipedia.org/wiki/Philosophy"))
			{
				System.out.println("made it to philosophy");
				break;
			}
		}
	}

	public static Element firstLink(Elements paragraphs)
	{
		Element link = null;
		for(Element e : paragraphs)
		{
			Iterable<Node> iter = new WikiNodeIterable(e);
			for (Node node : iter)		
			{
				if(node instanceof Element)
				{
					if(isValid((Element)node))
					{
						link = (Element)node;
					}
				}
			}
			if (link != null)
			{
				return link;
			}
			else
			{
				return null; //no links
			}
		}
		return null;
	}

	public static boolean isValid(Element el)
	{
		while(el != null)
		{
			if(el.tagName().equals("i") || el.tagName().equals("em"))
			{
				return false;
			}
			el = el.parent();
		}
		return true;
	}
}


