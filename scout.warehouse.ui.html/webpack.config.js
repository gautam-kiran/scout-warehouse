const baseConfig = require('@eclipse-scout/cli/scripts/webpack-defaults');

module.exports = (env, args) => {
  args.resDirArray = ['src/main/resources/WebContent', 'node_modules/@eclipse-scout/core/res'];
  const config = baseConfig(env, args);

  config.entry = {
    'warehouse': './src/main/js/warehouse.ts',
    'login': './src/main/js/login.ts',
    'logout': './src/main/js/logout.ts',
    'warehouse-theme': './src/main/js/warehouse-theme.less',
    'warehouse-theme-dark': './src/main/js/warehouse-theme-dark.less'
  };

  return config;
};
