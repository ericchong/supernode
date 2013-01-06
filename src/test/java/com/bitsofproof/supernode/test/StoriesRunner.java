package com.bitsofproof.supernode.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import org.jbehave.core.annotations.BeforeScenario;
import org.jbehave.core.annotations.Configure;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.UsingEmbedder;
import org.jbehave.core.annotations.UsingSteps;
import org.jbehave.core.annotations.When;
import org.jbehave.core.annotations.spring.UsingSpring;
import org.jbehave.core.embedder.Embedder;
import org.jbehave.core.io.CodeLocations;
import org.jbehave.core.io.StoryFinder;
import org.jbehave.core.junit.JUnitStories;
import org.jbehave.core.junit.spring.SpringAnnotatedEmbedderRunner;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.bitsofproof.supernode.api.ValidationException;
import com.bitsofproof.supernode.core.BitcoinNetwork;
import com.bitsofproof.supernode.core.Chain;

@Component
@RunWith (SpringAnnotatedEmbedderRunner.class)
@Configure
@UsingEmbedder (embedder = Embedder.class, generateViewAfterStories = false, ignoreFailureInStories = false, ignoreFailureInView = true, stepsFactory = true)
@UsingSpring (resources = "classpath:context/stories.xml")
@UsingSteps
public class StoriesRunner extends JUnitStories
{
	private static final Logger log = LoggerFactory.getLogger (StoriesRunner.class);

	@Autowired
	@Qualifier ("bitcoinNetwork")
	BitcoinNetwork network;

	@Autowired
	Chain chain;

	@Override
	protected List<String> storyPaths ()
	{
		return new StoryFinder ().findPaths (CodeLocations.codeLocationFromPath ("src/test/resources"), "*.story", "");
	}

	public void setNetwork (BitcoinNetwork network)
	{
		this.network = network;
	}

	@BeforeScenario
	public void init ()
	{
		try
		{
			network.getStore ().resetStore (chain);
			network.getStore ().cache (0);
			network.start ();
		}
		catch ( ValidationException e )
		{
			log.error ("can not set up story", e);
		}
		catch ( IOException e )
		{
			log.error ("can not set up story", e);
		}
		catch ( Exception e )
		{
			log.error ("bad luck", e);
		}
	}

	@When ("version message arrives")
	public void whenVersionMessage ()
	{
		assertFalse (false);
	}

	@Then ("connected")
	public void thenConnected ()
	{
		assertTrue (true);
	}
}
