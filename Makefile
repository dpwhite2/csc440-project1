
JCLASSPATH = $(CURDIR)/src
JPROJECT1PATH = $(JCLASSPATH)/edu/ncsu/csc/csc440/project1
JFLAGS = -g -cp $(JCLASSPATH):$(CLASSPATH)
JC = javac

.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = $(wildcard $(JPROJECT1PATH)/*.java)
CLASSES += $(wildcard $(JPROJECT1PATH)/menu/*.java)
CLASSES += $(wildcard $(JPROJECT1PATH)/db/*.java)

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class

