import 'dart:core';

import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

import 'backraund_platform_interface.dart';

/// An implementation of [BackraundPlatform] that uses method channels.
class MethodChannelBackraund extends BackraundPlatform {
  /// The method channel used to interact with the native platform.
  @visibleForTesting
  final methodChannel = const MethodChannel('backraund');

  @override
  Future<Uint8List?> remomeBackrond({required String path}) async {
    final List<int> result = await methodChannel
        .invokeMethod('removeBackraund', {"imagePath": path});
    // return version;
    // final List<int> result = await platform.invokeMethod('removeBackground', imagePath);
    final Uint8List uint8List = Uint8List.fromList(result);
    return uint8List;
  }
}
