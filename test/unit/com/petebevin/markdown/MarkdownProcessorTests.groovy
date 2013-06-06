package com.petebevin.markdown

import grails.test.mixin.TestMixin
import grails.test.mixin.support.GrailsUnitTestMixin

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestMixin(GrailsUnitTestMixin)
class MarkdownProcessorTests {

    void setUp() {
        // Setup logic here
    }

    void tearDown() {
        // Tear down logic here
    }

    void testSystemEmailMessageToMarkdown() {
        def emailMessage = """Dear Someone,

# Welcome!

**We** are so happy that you *chose* us!

> Indented block quote paragraph.
> > This is a nested blockquote.
> Back to the first level

1. First Item in a Numerical list
2. Second Item in a Numerical list

* First Item in a list
* Second Item in a list

+ First Item in a list
+ Second Item in a list

		Should be a code block.

Following this paragraph should be a horizontal line.

***

This is [an example](http://cogda.com/ "Title") inline link.

[This link](http://rapidcommittee.net/) has no title attribute."""
		
		MarkdownProcessor processor = new MarkdownProcessor();
		emailMessage = processor.markdown(emailMessage)
		
		assert emailMessage
		println emailMessage
    }
}
