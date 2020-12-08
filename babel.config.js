module.exports = {
    "presets": ["@babel/preset-env"],
    // plugins 추가 하면 됨
    "plugins": [
      [
        "module-resolver",
        {
          "alias": {
            "@": "./src/main/web-app"
          }
        }
      ]
    ]
  };