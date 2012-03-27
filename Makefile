
JCLASSPATH = $(CURDIR)/src
JPROJECT1PATH = $(JCLASSPATH)/edu/ncsu/csc/csc440/project1
ifeq ($(OS),Windows_NT)
	JFLAGS = -g -cp "$(JCLASSPATH);$(CLASSPATH)"
else
	JFLAGS = -g -cp $(JCLASSPATH):$(CLASSPATH)
endif
JC = javac

.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = $(wildcard $(JPROJECT1PATH)/*.java)
CLASSES += $(wildcard $(JPROJECT1PATH)/db/*.java)
CLASSES += $(wildcard $(JPROJECT1PATH)/objs/*.java)
CLASSES += $(wildcard $(JPROJECT1PATH)/menu/*.java)

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) $(JPROJECT1PATH)/*.class
	$(RM) $(JPROJECT1PATH)/db/*.class
	$(RM) $(JPROJECT1PATH)/objs/*.class
	$(RM) $(JPROJECT1PATH)/menu/*.class

