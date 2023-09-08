import 'dart:io';

import 'package:backraund/backraund.dart';
import 'package:flutter/material.dart';
import 'package:image_picker/image_picker.dart';

class MyApp extends StatefulWidget {
  const MyApp({super.key});

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  final backraundPlugin = Backraund();

  @override
  void initState() {
    super.initState();
  }

  // Platform messages are asynchronous, so we initialize in an async method.
  File? file;
  var result;

  callPlugin() async {
    result = await backraundPlugin.remomeBackrond(path: file!.path);
    setState(() {});
  }

  imagePiker() async {
    var selected = await ImagePicker().pickImage(source: ImageSource.gallery);
    if (selected == null) {
      return;
    }
    file = File(selected.path);
    setState(() {});
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Background Remover'),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            // Display your image here

            result != null
                ? Image.memory(
                    result!,
                    fit: BoxFit.fill,
                    height: 350,
                  )
                : file != null
                    ? Image.file(
                        file!,
                        fit: BoxFit.fill,
                        height: 350,
                      )
                    : const SizedBox(),

            const SizedBox(height: 20),
            ElevatedButton(
              onPressed: () async {
                imagePiker();
                // await bg.getPlatformVersion();
                // print("===============>${await bg.getPlatformVersion()}");
              },
              child: const Text('image pick'),
            ),
            ElevatedButton(
              onPressed: () async {
                callPlugin();
              },
              child: const Text('Remove Background'),
            ),
          ],
        ),
      ),
    );
  }
}
