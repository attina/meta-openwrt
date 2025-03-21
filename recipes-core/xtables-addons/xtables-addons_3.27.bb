SUMMARY = "non-mainline-kernel netfilter extensions"
DESCRIPTION = "Xtables-addons contains a set of possibly useful but not included in the mainline kernel nefilter extensions"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=751419260aa954499f7abaabaa882bbe"
DEPENDS = "virtual/kernel iptables"

inherit autotools kernel-module-split module-base pkgconfig

CFLAGS:append:toolchain-clang = " -Wno-error=implicit-function-declaration"

SRC_URI = " \
          https://inai.de/files/xtables-addons/${BP}.tar.xz \
          file://001-fix-kernel-version-detection.patch \
          file://100-add-rtsp-conntrack.patch \
          file://200-add-lua-packetscript.patch \
          file://202-add-lua-autoconf.patch \
          file://300-fix-path-Makefile.extra.patch \
          "
SRC_URI[sha256sum] = "e47ea8febe73c12ecab09d2c93578c5dc72d76f17fdf673397758f519cce6828"

MODULES_MODULE_SYMVERS_LOCATION = "../${BP}/extensions"

EXTRA_OECONF = "--with-kbuild=${STAGING_KERNEL_DIR}"

EXTRA_OEMAKE = "M=${S}/extentions DESTDIR=${D} V=1"
MODULES_INSTALL_TARGET = "install"
# make_scripts requires kernel source directory to create
# kernel scripts
do_make_scripts[depends] += "virtual/kernel:do_shared_workdir"

FILES:${PN} += "${libexecdir}/xtables-addons ${sbindir}/iptaccount ${libdir}/libxt_ACCOUNT_cl.so.* ${libdir}/iptables"

RDEPENDS:${PN} += "perl"
