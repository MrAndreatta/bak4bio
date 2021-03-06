class ContentsController < ApplicationController
  load_resource
  authorize_resource
  
  # GET /contents
  # GET /contents.json
  def index
    @contents = Content.accessible_by(current_ability).page params[:page]
    
    if params[:filter]
      @contents = @contents.where("description LIKE ?", "%#{params[:filter]}%")
    end

    respond_to do |format|
      format.html # index.html.erb
      format.json { render json: @contents }
    end
  end

  # GET /contents/1.json
  def show
    @content = Content.find(params[:id])

    respond_to do |format|
      format.json { render json: @content }
    end
  end

  # GET /contents/new
  # GET /contents/new.json
  def new
    @content = Content.new
    @content.owner_id = current_user.id

    respond_to do |format|
      format.html # new.html.erb
      format.json { render json: @content }
    end
  end

  # GET /contents/1/edit
  def edit
    @content = Content.find(params[:id])
  end

  # POST /contents
  # POST /contents.json
  def create
    @content = Content.new(params[:content])

    respond_to do |format|
      if @content.save
        format.html { redirect_to contents_path, notice: 'Content was successfully created.' }
        format.json { render json: @content, status: :created, location: @content }
      else
        format.html { render action: "new" }
        format.json { render json: @content.errors.full_messages, status: :unprocessable_entity }
      end
    end
  end

  # PUT /contents/1
  # PUT /contents/1.json
  def update
    @content = Content.find(params[:id])

    respond_to do |format|
      if @content.update_attributes(params[:content])
        format.html { redirect_to contents_path, notice: 'Content was successfully updated.' }
        format.json { head :no_content }
      else
        format.html { render action: "edit" }
        format.json { render json: @content.errors.full_messages, status: :unprocessable_entity }
      end
    end
  end

  # DELETE /contents/1
  # DELETE /contents/1.json
  def destroy
    @content = Content.find(params[:id])

    respond_to do |format|
      if @content.destroy
         format.html { redirect_to contents_url, notice: 'Content was successfully removed.' }
         format.json { head :no_content }
      else  
        format.html { redirect_to contents_url, alert: @content.errors.full_messages }
        format.json { render json: @content.errors.full_messages, status: :unprocessable_entity }
      end
    end
  end
  
  # GET /contents/1/download
  def download
    @content = Content.find(params[:id])
    
    send_file @content.source.path, :type => @content.source_content_type, :filename => @content.source_file_name
    
  end
end
