# To learn more about how to use Nix to configure your environment
# see: https://firebase.google.com/docs/studio/customize-workspace
{ pkgs, ... }: {
  # Which nixpkgs channel to use.
  channel = "stable-25.05"; #stable-24.05"; # or "unstable"

  services = {
    docker = {
      enable = true;
    };
  };

  # Use https://search.nixos.org/packages to find packages
  packages = [
    pkgs.maven
    pkgs.nodejs_22
    pkgs.jdk17
    #pkgs.graalvmPackages.graalvm-oracle
    pkgs.awscli2
    pkgs.aws-sam-cli
    pkgs.python313
    pkgs.python313Packages.pip
    pkgs.localstack
    pkgs.python313Packages.localstack-client
    pkgs.jq
    pkgs.zip
    pkgs.curl
  ];

  # Sets environment variables in the workspace
  env = {
    LOCALSTACK_AUTH_TOKEN="ls-JIRu0752-liKa-paYA-2551-dexIxEKA028e";
    AWS_ACCESS_KEY_ID="test";
    AWS_SECRET_ACCESS_KEY="test";
    AWS_DEFAULT_REGION="sa-east-1";
    ACTIVATE_PRO="0";
  };
  idx = {
    # Search for the extensions you want on https://open-vsx.org/ and use "publisher.id"
    extensions = [
      # "vscodevim.vim"
    ];

    # Enable previews
    previews = {
      enable = true;
      previews = {
        # web = {
        #   # Example: run "npm run dev" with PORT set to IDX's defined port for previews,
        #   # and show it in IDX's web preview panel
        #   command = ["npm" "run" "dev"];
        #   manager = "web";
        #   env = {
        #     # Environment variables to set for your server
        #     PORT = "$PORT";
        #   };
        # };
      };
    };

    # Workspace lifecycle hooks
    workspace = {
      # Runs when a workspace is first created
      onCreate = {
        # Example: install JS dependencies from NPM
        # npm-install = "npm install";
      };
      # Runs when the workspace is (re)started
      onStart = {
        # Example: start a background task to watch and re-build backend code
        # watch-backend = "npm run watch-backend";
      };
    };
  };
}
