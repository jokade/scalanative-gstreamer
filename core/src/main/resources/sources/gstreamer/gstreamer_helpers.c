#include <gst/gst.h>

gpointer snhelper_gst_message_src(GstMessage *msg) {
  return GST_MESSAGE_SRC(msg);
}