#!/usr/bin/env python

import os
import datetime

from flask import Flask, jsonify
app = Flask(__name__)

@app.route("/", methods=["GET"])
def hello():
    return jsonify(message="Hello from Python Version Tracker Example!",
                   hostname=os.getenv("HOSTNAME"),
                   time=datetime.datetime.now().isoformat()), 200


@app.route("/health", methods=["GET", "HEAD"])
def health():
    return "", 200

def main():
    app.run(debug=False, host="0.0.0.0")


if __name__ == "__main__":
    main()
