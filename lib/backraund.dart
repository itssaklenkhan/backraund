import 'dart:typed_data';

import 'backraund_platform_interface.dart';

class Backraund {
  Future<Uint8List?> remomeBackrond({required String path}) {
    return BackraundPlatform.instance.remomeBackrond(path: path);
  }
}
