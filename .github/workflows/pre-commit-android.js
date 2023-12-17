// Example pre-commit hook function
exports.preCommit = (props) => {
  // Extract current version
  const currentVersion = process.env.CURRENT_VERSION;

  console.log(currentVersion);

  // Parse commits to determine version bump type
  const bumpType = determineBumpType(props.commits);

  // Calculate new version based on bump type and current version
  const newVersion = applyBump(currentVersion, bumpType);

  // Update props with new version
  props.version = newVersion;

  // Optionally log or perform other actions with the new version
  console.log(`Next version: ${newVersion}`);

  // Keep modified commits or filter based on bump type (optional)
  // ...
};

// Function to determine bump type based on commit messages (example)
function determineBumpType(commits) {
  for (const commit of commits) {
    const message = commit.message;
    if (
      message.includes("feat:") ||
      message.includes("feat!:") ||
      message.includes("BREAKING")
    ) {
      return "major";
    } else if (message.includes("fix:") || message.includes("refactor:")) {
      return "minor";
    } else {
      return "patch";
    }
  }
  return "patch"; // Default to patch if no specific bump type identified
}

// Function to apply bump based on type and current version (example)
function applyBump(currentVersion, bumpType) {
  const [major, minor, patch] = currentVersion.split(".");
  switch (bumpType) {
    case "major":
      return `${Number(major) + 1}.0.0`;
    case "minor":
      return `${major}.${Number(minor) + 1}.0`;
    case "patch":
      return `${major}.${minor}.${Number(patch) + 1}`;
    default:
      return currentVersion; // Fallback to current version if unknown type
  }
}
