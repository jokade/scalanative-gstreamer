CFLAGS=$(shell pkg-config --cflags gstreamer-1.0)

gstreamer_helpers.o:
	$(CC) $(CFLAGS) -c gstreamer_helpers.c $<

clean:
	rm -f gstreamer_helpers.o

.PHONY: clean