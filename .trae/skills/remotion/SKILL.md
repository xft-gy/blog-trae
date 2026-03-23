---
name: "remotion"
description: "Enables AI to create videos programmatically using React with Remotion framework. Invoke when user needs to generate video code."
---

# Remotion Skill

This skill enables AI to create videos programmatically using React with the Remotion framework. It provides guidelines for AI to correctly use Remotion API and follow best practices for video creation.

## What is Remotion?

Remotion is a framework for creating videos programmatically using React. It allows you to:
- Leverage web technologies: Use all of CSS, Canvas, SVG, WebGL, etc.
- Leverage programming: Use variables, functions, APIs, math and algorithms to create new effects
- Leverage React: Reusable components, Powerful composition, Fast Refresh, Package ecosystem

## Core Skill Modules

### animations
- Animation capabilities: Use useCurrentFrame, interpolate, spring APIs to create frame-based animations
- Best practices for smooth animations
- Timing and easing functions

### compositions
- Composition definition规范: Declare video dimensions, frame rate, duration, and video structure
- Best practices for organizing video content
- Scene management and transitions

### audio
- Audio processing: Load audio, crop, control volume, align with timeline
- Audio synchronization techniques
- Audio effects and mixing

### assets
- Resource management: Loading images, videos, audio, fonts, and other static resources
- Asset optimization techniques
- Proper asset organization

### images
- Image processing: Using <Img /> component to display images
- Image optimization and resizing
- Image effects and transformations

### gifs
- GIF support: Embedding GIFs in videos
- GIF optimization techniques
- Controlling GIF playback

### lottie
- Lottie animation support: Integrating Lottie animations in Remotion videos
- Best practices for Lottie integration
- Performance optimization for Lottie

## Installation

To use Remotion in your project:

1. Create a new Remotion project:
   ```bash
   npx create-video@latest
   ```

2. Install Remotion Skills:
   ```bash
   npx skills add remotion-dev/skills
   ```

3. Start the development server:
   ```bash
   npm run dev
   ```

## Usage

1. **Activate the skill** in your AI assistant
2. **Describe your video** using natural language
3. **Review the generated code** and make any necessary adjustments
4. **Render the video** using Remotion's rendering commands

## Best Practices

- **Component organization**: Break down your video into reusable components
- **Animation techniques**: Use Remotion's built-in animation APIs for smooth effects
- **Performance optimization**: Keep animations efficient to avoid rendering issues
- **Asset management**: Optimize assets for video rendering
- **Timeline management**: Use Remotion's timeline features for precise timing

## Example Prompt

```
Create a 10-second product showcase video with:
- Fade-in logo at the beginning
- Three product images rotating in the middle
- Upbeat background music
- Brand colors: blue and black
- Smooth transitions between scenes
```

The AI will generate complete Remotion code that implements this video using best practices.
