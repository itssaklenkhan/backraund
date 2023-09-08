import 'dart:typed_data';

import 'package:plugin_platform_interface/plugin_platform_interface.dart';

import 'backraund_method_channel.dart';

abstract class BackraundPlatform extends PlatformInterface {
  /// Constructs a BackraundPlatform.
  BackraundPlatform() : super(token: _token);

  static final Object _token = Object();

  static BackraundPlatform _instance = MethodChannelBackraund();

  /// The default instance of [BackraundPlatform] to use.
  ///
  /// Defaults to [MethodChannelBackraund].
  static BackraundPlatform get instance => _instance;

  /// Platform-specific implementations should set this with their own
  /// platform-specific class that extends [BackraundPlatform] when
  /// they register themselves.
  static set instance(BackraundPlatform instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  Future<Uint8List?> remomeBackrond({required String path}) {
    throw UnimplementedError('path is null');
  }
}
